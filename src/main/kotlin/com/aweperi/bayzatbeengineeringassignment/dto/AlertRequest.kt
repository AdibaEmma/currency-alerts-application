package com.aweperi.bayzatbeengineeringassignment.dto

import com.aweperi.bayzatbeengineeringassignment.model.AlertStatus
import java.math.BigDecimal

data class AlertRequest(
    var targetPrice: BigDecimal,
    var status: AlertStatus
)