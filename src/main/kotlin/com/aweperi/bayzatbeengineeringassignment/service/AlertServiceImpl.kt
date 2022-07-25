package com.aweperi.bayzatbeengineeringassignment.service

import com.aweperi.bayzatbeengineeringassignment.error_handling.exceptions.AlertNotFoundException
import com.aweperi.bayzatbeengineeringassignment.error_handling.exceptions.CurrencyNotFoundException
import com.aweperi.bayzatbeengineeringassignment.error_handling.exceptions.InvalidStatusTransitionException
import com.aweperi.bayzatbeengineeringassignment.error_handling.exceptions.UserNotFoundException
import com.aweperi.bayzatbeengineeringassignment.model.Alert
import com.aweperi.bayzatbeengineeringassignment.model.AlertStatus
import com.aweperi.bayzatbeengineeringassignment.repository.AlertRepository
import com.aweperi.bayzatbeengineeringassignment.repository.CurrencyRepository
import com.aweperi.bayzatbeengineeringassignment.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class AlertServiceImpl(
    private val alertRepository: AlertRepository,
    private val currencyRepository: CurrencyRepository,
    private val userRepository: UserRepository,
) : AlertService {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun createAlert(userId: Long, currencySymbol: String, alertRequest: Alert): Alert {
        val foundCurrency = this.currencyRepository.findBySymbol(currencySymbol)

        if (foundCurrency.isPresent)
            if (alertRequest.targetPrice >= foundCurrency.get().currentPrice) alertRequest.currency =
                foundCurrency.get()
            else throw IllegalArgumentException("target price must be greater than current price of currency")
        else throw CurrencyNotFoundException()
        val foundUser = this.userRepository.findByIdOrNull(userId) ?: throw UserNotFoundException()
        alertRequest.user = foundUser
        val savedAlert = this.alertRepository.save(alertRequest)
        foundUser.addUserAlert(savedAlert)
        return savedAlert
    }
    override fun getAlerts(userId: Long, currencySymbol: String?): List<Alert> {
        return if(currencySymbol != null ) this.getAlertsByCurrencySymbol(userId, currencySymbol)
        else this.alertRepository.findAll().toList()
    }

    override fun getAlertsByCurrencySymbol(userId: Long, currencySymbol: String): List<Alert> {
        return this.alertRepository.findAllByCurrency_Symbol(currencySymbol)
    }

    override fun getAlert(userId: Long, alertId: Long): Alert {
        this.userRepository.findByIdOrNull(userId) ?: throw UserNotFoundException()
        return this.alertRepository.findByIdOrNull(alertId) ?: throw AlertNotFoundException()
    }

    override fun updateAlert(userId: Long, alertId: Long, updateRequest: Map<String, Any>): Alert {
        val foundAlert = this.getAlert(userId, alertId)
        updateRequest.forEach { (change: String?, value: Any?) ->
            when (change) {
                "targetPrice" -> foundAlert.targetPrice = BigDecimal.valueOf(value as Double)
                "status" -> {
                    val status: AlertStatus = AlertStatus.valueOf(value as String)

                    if (status == AlertStatus.CANCELED)
                        if (foundAlert.status != AlertStatus.TRIGGERRED)
                            foundAlert.status = AlertStatus.CANCELED
                        else throw InvalidStatusTransitionException()
                    if (status == AlertStatus.ACKED)
                        if (foundAlert.status == AlertStatus.TRIGGERRED)
                            foundAlert.status = AlertStatus.ACKED
                        else throw InvalidStatusTransitionException()

                    foundAlert.status = status
                }
            }
        }
        return this.alertRepository.save(foundAlert)
    }

    override fun deleteAlert(userId: Long, alertId: Long) {
        this.getAlert(userId, alertId)
        this.alertRepository.deleteById(alertId)
    }

    @Scheduled(cron = "*/30 * * * * *")
    private fun checkTargetPriceMatch() {
        val alerts = this.alertRepository.findAll()

        alerts.forEach {
            if (it.targetPrice == it.currency?.currentPrice)
                logger.info("Alert Trigger: Hi {} {} has reached target price", it.user!!.username, it.alertId)
        }
    }
}