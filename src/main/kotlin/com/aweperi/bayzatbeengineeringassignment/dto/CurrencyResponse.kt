package com.aweperi.bayzatbeengineeringassignment.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import java.math.BigDecimal
import java.time.LocalDateTime

data class CurrencyResponse(
    @JsonIgnore
    val id: Long?,
    val currencyId: String?,
    val name: String,
    val symbol: String,
    val currentPrice: BigDecimal,
    val enabled: Boolean = true,
    val createdTime: LocalDateTime,
)