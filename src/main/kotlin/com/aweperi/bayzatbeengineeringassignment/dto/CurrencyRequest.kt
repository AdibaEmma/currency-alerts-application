package com.aweperi.bayzatbeengineeringassignment.dto

import java.math.BigDecimal
import javax.validation.constraints.NotBlank


data class CurrencyRequest(
    @NotBlank(message = "Currency name cannot be null or empty")
    var name: String,
    @NotBlank(message = "Currency symbol cannot be null or empty")
    var symbol: String,
    var currentPrice: BigDecimal,
    var enabled: Boolean = true,
)