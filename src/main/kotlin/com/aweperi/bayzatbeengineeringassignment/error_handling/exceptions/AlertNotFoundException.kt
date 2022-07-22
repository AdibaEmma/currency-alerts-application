package com.aweperi.bayzatbeengineeringassignment.error_handling.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class AlertNotFoundException: RuntimeException {
    constructor() : super("No alert found")
    constructor(message : String) : super(message)
}
