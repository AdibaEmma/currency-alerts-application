package com.aweperi.bayzatbeengineeringassignment.controller

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.lang.Nullable

object ResponseHandler {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val jsonResponse = LinkedHashMap<String, Any?>()
    fun handleResponseBody(statusCode: HttpStatus, message: String?, @Nullable payload: Any?): ResponseEntity<*> {
        jsonResponse["status"] = statusCode.value()
        jsonResponse["message"] = message
        jsonResponse["payload"] = payload
        if (payload == null) jsonResponse["payload"] = null
        logger.info(jsonResponse.toString())
        return ResponseEntity.status(statusCode).body(jsonResponse)
    }
}