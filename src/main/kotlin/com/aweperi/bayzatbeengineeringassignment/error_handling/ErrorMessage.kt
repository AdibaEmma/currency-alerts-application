package com.aweperi.bayzatbeengineeringassignment.error_handling

import java.util.*

data class ErrorMessage(
    private val statusCode: Int,
    private val timestamp: Date,
    private val message: String,
    private val description: String?,
)