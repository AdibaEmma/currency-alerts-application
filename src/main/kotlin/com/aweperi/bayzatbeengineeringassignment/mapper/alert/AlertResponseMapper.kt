package com.aweperi.bayzatbeengineeringassignment.mapper.alert

import com.aweperi.bayzatbeengineeringassignment.dto.AlertResponse
import com.aweperi.bayzatbeengineeringassignment.mapper.Mapper
import com.aweperi.bayzatbeengineeringassignment.model.Alert
import org.springframework.stereotype.Component

@Component
class AlertResponseMapper: Mapper<Alert, AlertResponse> {
    override fun transform(source: Alert): AlertResponse {
        return AlertResponse(
            source.alertId!!,
            source.currency!!.symbol,
            source.targetPrice,
            source.status,
            source.createdAt,
        )
    }
}