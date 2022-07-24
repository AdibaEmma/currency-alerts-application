package com.aweperi.bayzatbeengineeringassignment.dto

data class UserResponse(
    val userId: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    val username: String,
    val password: String?
)
