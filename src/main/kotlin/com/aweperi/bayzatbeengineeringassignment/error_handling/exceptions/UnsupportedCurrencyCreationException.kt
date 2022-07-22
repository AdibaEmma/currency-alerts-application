package com.aweperi.bayzatbeengineeringassignment.error_handling.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class UnsupportedCurrencyCreationException: RuntimeException {
    constructor() : super("Unsupported Currency Value.")
    constructor(message: String?) : super(message)
}