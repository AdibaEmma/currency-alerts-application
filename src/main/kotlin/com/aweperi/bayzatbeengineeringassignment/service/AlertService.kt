package com.aweperi.bayzatbeengineeringassignment.service

import com.aweperi.bayzatbeengineeringassignment.model.Alert

interface AlertService {
    fun createAlert(userId: Long, currencySymbol: String, alertRequest: Alert): Alert
    fun getAlerts(userId: Long, currencySymbol: String?): List<Alert>
    fun getAlertsByCurrencySymbol(userId: Long, currencySymbol: String): List<Alert>
    fun getAlert(userId: Long, alertId: Long): Alert
    fun updateAlert(userId: Long, alertId: Long, updateRequest: Map<String, Any>): Alert
    fun deleteAlert(userId: Long, alertId: Long)
}