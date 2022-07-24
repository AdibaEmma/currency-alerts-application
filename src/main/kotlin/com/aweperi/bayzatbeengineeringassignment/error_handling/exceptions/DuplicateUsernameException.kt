package com.aweperi.bayzatbeengineeringassignment.error_handling.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.CONFLICT)
class DuplicateUsernameException: RuntimeException {
    constructor() : super("Username already exists.")
    constructor(message: String?) : super(message)
}