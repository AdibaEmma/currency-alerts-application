package com.aweperi.bayzatbeengineeringassignment.security

import com.aweperi.bayzatbeengineeringassignment.security.config.SecurityProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
class PasswordEncoder(val securityProperties: SecurityProperties) {
    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder = BCryptPasswordEncoder(securityProperties.strength)
}