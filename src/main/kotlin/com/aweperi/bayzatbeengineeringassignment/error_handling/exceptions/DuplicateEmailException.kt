package com.aweperi.bayzatbeengineeringassignment.error_handling.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.CONFLICT)
class DuplicateEmailException: RuntimeException {
    constructor() : super("Email already exists.")
    constructor(message: String?) : super(message)
}