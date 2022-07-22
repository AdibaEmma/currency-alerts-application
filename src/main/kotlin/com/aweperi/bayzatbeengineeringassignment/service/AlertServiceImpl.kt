package com.aweperi.bayzatbeengineeringassignment.service

import com.aweperi.bayzatbeengineeringassignment.dto.AlertRequest
import com.aweperi.bayzatbeengineeringassignment.model.Alert
import com.aweperi.bayzatbeengineeringassignment.repository.AlertRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AlertServiceImpl(@Autowired private val alertRepository: AlertRepository): AlertService {
    override fun createAlert(currencyId: Long, alertRequest: AlertRequest): Alert {
        TODO("Not yet implemented")
    }

    override fun getAlerts(): List<AlertRequest> {
        TODO("Not yet implemented")
    }

    override fun getAlertsByCurrencySymbol(currencySymbol: String): List<AlertRequest> {
        TODO("Not yet implemented")
    }

    override fun getAlert(alertId: Long): Alert {
        TODO("Not yet implemented")
    }

    override fun updateAlert(alertId: Long, updateAlertRequest: AlertRequest) {
        TODO("Not yet implemented")
    }

    override fun cancelAlert(alertId: Long) {
        TODO("Not yet implemented")
    }

    override fun acknowledgeAlert(alertId: Long): Alert {
        TODO("Not yet implemented")
    }

    override fun deleteAlert(alertId: Long) {
        TODO("Not yet implemented")
    }
}