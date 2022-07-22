package com.aweperi.bayzatbeengineeringassignment.mapper.alert

import com.aweperi.bayzatbeengineeringassignment.dto.AlertRequest
import com.aweperi.bayzatbeengineeringassignment.mapper.Mapper
import com.aweperi.bayzatbeengineeringassignment.model.Alert
import com.aweperi.bayzatbeengineeringassignment.model.AlertStatus
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class AlertRequestMapper: Mapper<AlertRequest, Alert> {
    override fun transform(source: AlertRequest): Alert {
        return Alert(
            null,
            source.targetPrice,
            AlertStatus.NEW,
            LocalDateTime.now(),
            null
            )
    }
}