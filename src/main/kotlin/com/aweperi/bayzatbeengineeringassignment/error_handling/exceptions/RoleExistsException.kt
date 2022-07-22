package com.aweperi.bayzatbeengineeringassignment.error_handling.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class RoleExistsException: RuntimeException {
    constructor() : super("User already has role.")
    constructor(message: String?) : super(message)
}