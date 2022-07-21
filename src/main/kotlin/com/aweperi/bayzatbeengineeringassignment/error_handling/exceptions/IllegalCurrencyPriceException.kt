package com.aweperi.bayzatbeengineeringassignment.error_handling.exceptions

class IllegalCurrencyPriceException: RuntimeException {
    constructor() : super("Currency price must be greater than zero.")
    constructor(message: String?) : super(message)
}