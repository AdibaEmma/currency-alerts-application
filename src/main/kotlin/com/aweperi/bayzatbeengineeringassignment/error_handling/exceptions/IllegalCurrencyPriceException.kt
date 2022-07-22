package com.aweperi.bayzatbeengineeringassignment.error_handling.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
class IllegalCurrencyPriceException: RuntimeException {
    constructor() : super("Currency price must be greater than zero.")
    constructor(message: String?) : super(message)
}