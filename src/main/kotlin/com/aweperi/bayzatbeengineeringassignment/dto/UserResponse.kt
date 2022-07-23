package com.aweperi.bayzatbeengineeringassignment.dto

import org.springframework.security.core.GrantedAuthority

data class UserResponse(
    val userId: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val username: String,
    val password: String?,
    val userRoles: Collection<GrantedAuthority?>
)
