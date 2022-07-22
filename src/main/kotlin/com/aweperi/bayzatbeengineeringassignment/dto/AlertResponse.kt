package com.aweperi.bayzatbeengineeringassignment.dto

import com.aweperi.bayzatbeengineeringassignment.model.AlertStatus
import java.math.BigDecimal
import java.time.LocalDateTime

data class AlertResponse(
    var alertId: Long,
    var currencySymbol: String,
    var targetPrice: BigDecimal,
    var status: AlertStatus,
    var createdAt: LocalDateTime,
)
