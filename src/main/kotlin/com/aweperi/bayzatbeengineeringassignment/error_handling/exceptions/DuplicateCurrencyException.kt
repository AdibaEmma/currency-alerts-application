package com.aweperi.bayzatbeengineeringassignment.error_handling.exceptions

class DuplicateCurrencyException: RuntimeException {
    constructor() : super("Currency already exists.")
    constructor(message: String?) : super(message)
}