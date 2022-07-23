package com.aweperi.bayzatbeengineeringassignment.security.config

import org.hibernate.validator.constraints.Length
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt-security")
class SecurityProperties {
    @Length(min = 42, message = "Minimum length for the secret is 42.")
    var secret = ""
    var expirationTime: Int = 30 * 60 * 1000
    var tokenPrefix = "Bearer "
    var strength = 10
}