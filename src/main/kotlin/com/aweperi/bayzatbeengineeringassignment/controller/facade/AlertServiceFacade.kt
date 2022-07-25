package com.aweperi.bayzatbeengineeringassignment.controller.facade

import com.aweperi.bayzatbeengineeringassignment.dto.AlertRequest
import com.aweperi.bayzatbeengineeringassignment.dto.AlertResponse
import com.aweperi.bayzatbeengineeringassignment.mapper.alert.AlertRequestMapper
import com.aweperi.bayzatbeengineeringassignment.mapper.alert.AlertResponseMapper
import com.aweperi.bayzatbeengineeringassignment.service.AlertService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class AlertServiceFacade(
    @Autowired private val alertService: AlertService,
    @Autowired private val alertRequestMapper: AlertRequestMapper,
    @Autowired private val alertResponseMapper: AlertResponseMapper,
) {
    fun createAlert(userId: Long, currencySymbol: String, alertRequest: AlertRequest): AlertResponse {
        val transformedRequest = alertRequestMapper.transform(alertRequest)
        return alertResponseMapper.transform(alertService.createAlert(userId, currencySymbol, transformedRequest))
    }

    fun getAlerts(userId: Long, currencySymbol: String?): List<AlertResponse> {
        return alertService.getAlerts(userId, null).map { alertResponseMapper.transform(it) }
    }

    fun getAlert(userId: Long, alertId: Long): AlertResponse {
        return alertResponseMapper.transform(alertService.getAlert(userId, alertId))
    }

    fun updateAlert(userId: Long, alertId: Long, updateRequest: Map<String, Any>): AlertResponse {
        return  alertResponseMapper.transform(alertService.updateAlert(userId, alertId, updateRequest))
    }

    fun toggleAlertStatus(userId: Long, alertId: Long, updateRequest: Map<String, Any>): AlertResponse {
        return alertResponseMapper.transform(alertService.updateAlert(userId, alertId, updateRequest))
    }

    fun deleteAlert(userId: Long, alertId: Long) {
        return alertService.deleteAlert(userId, alertId)
    }

}