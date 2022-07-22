package com.aweperi.bayzatbeengineeringassignment.error_handling.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.CONFLICT)
class DuplicateCurrencyException: RuntimeException {
    constructor() : super("Currency already exists.")
    constructor(message: String?) : super(message)
}