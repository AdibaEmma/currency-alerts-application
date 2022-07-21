package com.aweperi.bayzatbeengineeringassignment.dto

import java.math.BigDecimal
import java.time.LocalDateTime

data class CurrencyResponse(
    val id: Long?,
    val name: String,
    val symbol: String,
    val currentPrice: BigDecimal,
    val enabled: Boolean = true,
    val createdTime: LocalDateTime,
)