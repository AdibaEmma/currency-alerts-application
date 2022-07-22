package com.aweperi.bayzatbeengineeringassignment.controller.facade

import com.aweperi.bayzatbeengineeringassignment.dto.AlertRequest
import com.aweperi.bayzatbeengineeringassignment.dto.AlertResponse
import com.aweperi.bayzatbeengineeringassignment.mapper.alert.AlertRequestMapper
import com.aweperi.bayzatbeengineeringassignment.mapper.alert.AlertResponseMapper
import com.aweperi.bayzatbeengineeringassignment.model.AlertStatus
import com.aweperi.bayzatbeengineeringassignment.service.AlertService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class AlertServiceFacade(@Autowired private val alertService: AlertService,
                         @Autowired private val alertRequestMapper: AlertRequestMapper,
                         @Autowired private val alertResponseMapper: AlertResponseMapper
) {
    fun createAlert(currencySymbol: String, alertRequest: AlertRequest): AlertResponse {
        val transformedRequest = alertRequestMapper.transform(alertRequest)
        return alertResponseMapper.transform(alertService.createAlert(currencySymbol, transformedRequest))
    }

    fun getAlerts(): List<AlertResponse> {
        return alertService.getAlerts().map { alertResponseMapper.transform(it) }
    }

    fun getAlertsByCurrencySymbol(currencySymbol: String): List<AlertResponse> {
        return alertService.getAlertsByCurrencySymbol(currencySymbol).map { alertResponseMapper.transform(it) }
    }

    fun getAlert(alertId: Long): AlertResponse {
        return alertResponseMapper.transform(alertService.getAlert(alertId))
    }

    fun updateAlert(alertId: Long, alertRequest: AlertRequest) {
        val transformedRequest = alertRequestMapper.transform(alertRequest)
        return alertService.updateAlert(alertId, transformedRequest)
    }

    fun toggleAlertStatus(alertId: Long, status: AlertStatus): AlertResponse {
        return alertResponseMapper.transform(alertService.toggleAlertStatus(alertId, status))
    }

    fun deleteAlert(alertId: Long) {
        return alertService.deleteAlert(alertId)
    }

}