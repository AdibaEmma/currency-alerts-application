package com.aweperi.bayzatbeengineeringassignment.exceptions

class UnsupportedCurrencyCreationException: RuntimeException {
    constructor() : super("Unsupported Currency Value.")
    constructor(message: String?) : super(message)
}