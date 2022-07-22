package com.aweperi.bayzatbeengineeringassignment.service

import com.aweperi.bayzatbeengineeringassignment.dto.AlertRequest
import com.aweperi.bayzatbeengineeringassignment.model.Alert
import java.math.BigDecimal

interface AlertService {
    fun createAlert(currencyId: Long, alertRequest: AlertRequest): Alert
    fun getAlerts(): List<AlertRequest>
    fun getAlertsByCurrencySymbol(currencySymbol: String): List<AlertRequest>
    fun getAlert(alertId: Long): Alert
    fun updateAlert(alertId: Long, updateAlertRequest: AlertRequest)
    fun cancelAlert(alertId: Long)
    fun acknowledgeAlert(alertId: Long): Alert
    fun deleteAlert(alertId: Long)
}