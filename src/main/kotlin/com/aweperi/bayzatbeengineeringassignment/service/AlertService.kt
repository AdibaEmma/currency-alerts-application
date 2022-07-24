package com.aweperi.bayzatbeengineeringassignment.service

import com.aweperi.bayzatbeengineeringassignment.model.Alert
import com.aweperi.bayzatbeengineeringassignment.model.AlertStatus

interface AlertService {
    fun createAlert(userId: Long, currencySymbol: String, alertRequest: Alert): Alert
    fun getAlerts(): List<Alert>
    fun getAlertsByCurrencySymbol(currencySymbol: String): List<Alert>
    fun getAlert(alertId: Long): Alert
    fun updateAlert(alertId: Long, updateAlertRequest: Alert)
    fun toggleAlertStatus(alertId: Long, alertStatus: AlertStatus): Alert
    fun deleteAlert(alertId: Long)
}