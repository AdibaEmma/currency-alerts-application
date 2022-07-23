package com.aweperi.bayzatbeengineeringassignment.security.config

import com.benasher44.uuid.Uuid

object AuthConfig {
    private val secret = Uuid.randomUUID().toString()
    fun getSecret(): String {
        return secret
    }

}