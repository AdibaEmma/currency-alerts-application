package com.aweperi.bayzatbeengineeringassignment.error_handling.exceptions

class CurrencyNotFoundException: RuntimeException {
    constructor() : super("No currency found")
    constructor(message : String) : super(message)
}
