package com.aweperi.bayzatbeengineeringassignment.dto

import java.math.BigDecimal
import java.time.LocalDateTime

data class CurrencyRequest(
    var name: String,
    var symbol: String,
    var currentPrice: BigDecimal,
    var enabled: Boolean = true,
    var createdTime: LocalDateTime
)