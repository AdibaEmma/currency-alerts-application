package com.aweperi.bayzatbeengineeringassignment.error_handling

import java.util.*

data class ErrorMessage(
    val statusCode: Int,
    val timestamp: Date,
    val message: String,
    val description: String?,
)