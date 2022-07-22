package com.aweperi.bayzatbeengineeringassignment.service

import com.aweperi.bayzatbeengineeringassignment.error_handling.exceptions.AlertNotFoundException
import com.aweperi.bayzatbeengineeringassignment.error_handling.exceptions.CurrencyNotFoundException
import com.aweperi.bayzatbeengineeringassignment.model.Alert
import com.aweperi.bayzatbeengineeringassignment.model.AlertStatus
import com.aweperi.bayzatbeengineeringassignment.repository.AlertRepository
import com.aweperi.bayzatbeengineeringassignment.repository.CurrencyRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class AlertServiceImpl(private val alertRepository: AlertRepository,
                        private val currencyRepository: CurrencyRepository): AlertService {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun createAlert(currencySymbol: String, alertRequest: Alert): Alert {
        val foundCurrency = this.currencyRepository.findBySymbol(currencySymbol)

        if(foundCurrency != null)
            if(alertRequest.targetPrice <= foundCurrency.currentPrice) alertRequest.currency = foundCurrency
                else throw IllegalArgumentException("target price must be greater than current price of currency")
        else throw CurrencyNotFoundException()
        return this.alertRepository.save(alertRequest)
    }

    override fun getAlerts(): List<Alert> {
        return this.alertRepository.findAll().toList()
    }

    override fun getAlertsByCurrencySymbol(currencySymbol: String): List<Alert> {
        return this.alertRepository.findAllByCurrency_Symbol(currencySymbol)
    }

    override fun getAlert(alertId: Long): Alert {
        return this.alertRepository.findByIdOrNull(alertId) ?: throw AlertNotFoundException()
    }

    override fun updateAlert(alertId: Long, updateAlertRequest: Alert) {
        val foundAlert = this.getAlert(alertId)
        foundAlert.targetPrice = updateAlertRequest.targetPrice
        foundAlert.status = updateAlertRequest.status
        this.alertRepository.save(foundAlert)
    }

    override fun toggleAlertStatus(alertId: Long, alertStatus: AlertStatus): Alert {
        val foundAlert = this.getAlert(alertId)
        if(alertStatus == AlertStatus.CANCELED)
            if(foundAlert.status != AlertStatus.TRIGGERRED)
                foundAlert.status = AlertStatus.CANCELED
        if(alertStatus == AlertStatus.ACKED)
            if(foundAlert.status == AlertStatus.TRIGGERRED)
            foundAlert.status = AlertStatus.ACKED
        return this.alertRepository.save(foundAlert)
    }

    override fun deleteAlert(alertId: Long) {
        this.getAlert(alertId)
        this.alertRepository.deleteById(alertId)
    }

    @Scheduled(cron = "*/30 * * * * *")
    private fun checkTargetPriceMatch() {
        val alerts = this.alertRepository.findAll()

        alerts.forEach {
            if (it.targetPrice == it.currency?.currentPrice)
                logger.info("Alert Trigger: {} has reached target price", it.alertId)
        }
    }
}