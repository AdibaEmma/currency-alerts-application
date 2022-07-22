package com.aweperi.bayzatbeengineeringassignment.dto

import com.aweperi.bayzatbeengineeringassignment.model.AlertStatus
import com.aweperi.bayzatbeengineeringassignment.model.Currency
import java.math.BigDecimal
import java.time.LocalDateTime

data class AlertResponse(
    var currencySymbol: String,
    var targetPrice: BigDecimal,
    var status: AlertStatus,
    var createdAt: LocalDateTime,
)
