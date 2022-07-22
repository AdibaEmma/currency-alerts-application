package com.aweperi.bayzatbeengineeringassignment.error_handling.exceptions

class AlertNotFoundException: RuntimeException {
    constructor() : super("No alert found")
    constructor(message : String) : super(message)
}
