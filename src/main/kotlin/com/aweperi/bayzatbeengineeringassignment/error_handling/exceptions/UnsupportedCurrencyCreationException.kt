package com.aweperi.bayzatbeengineeringassignment.error_handling.exceptions

class UnsupportedCurrencyCreationException: RuntimeException {
    constructor() : super("Unsupported Currency Value.")
    constructor(message: String?) : super(message)
}