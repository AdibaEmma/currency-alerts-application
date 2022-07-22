package com.aweperi.bayzatbeengineeringassignment.mapper.alert

import com.aweperi.bayzatbeengineeringassignment.dto.AlertRequest
import com.aweperi.bayzatbeengineeringassignment.mapper.Mapper
import com.aweperi.bayzatbeengineeringassignment.model.Alert
import java.time.LocalDateTime

class AlertRequestMapper: Mapper<AlertRequest, Alert> {
    override fun transform(source: AlertRequest): Alert {
        return Alert(
            null,
            source.targetPrice,
            source.status,
            LocalDateTime.now(),
            null
            )
    }
}