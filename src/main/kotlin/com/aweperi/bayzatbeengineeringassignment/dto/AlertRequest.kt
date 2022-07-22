package com.aweperi.bayzatbeengineeringassignment.dto

import java.math.BigDecimal
import javax.validation.constraints.NotNull

data class AlertRequest(
    var targetPrice: BigDecimal
)