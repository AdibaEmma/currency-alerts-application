package com.aweperi.bayzatbeengineeringassignment.error_handling.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class UserNotFoundException: RuntimeException {
    constructor() : super("No user found")
    constructor(message : String) : super(message)
}
