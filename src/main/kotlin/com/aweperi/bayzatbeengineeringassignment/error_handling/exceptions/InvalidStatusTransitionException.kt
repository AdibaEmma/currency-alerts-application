package com.aweperi.bayzatbeengineeringassignment.error_handling.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.FORBIDDEN)
class InvalidStatusTransitionException: RuntimeException {
    constructor() : super("Cannot change alert status.")
    constructor(message: String?) : super(message)
}