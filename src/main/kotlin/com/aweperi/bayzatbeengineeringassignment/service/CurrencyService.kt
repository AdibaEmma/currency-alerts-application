package com.aweperi.bayzatbeengineeringassignment.service

import com.aweperi.bayzatbeengineeringassignment.model.Currency

interface CurrencyService {
    fun addCurrency(currency: Currency): Currency
    fun fetchCurrencies(): List<Currency>
    fun getCurrency(value: Any): Currency
    fun updateCurrency(currencyId: Long)
    fun toggleCurrencyStatus(currencyId: Long): Currency
    fun deleteCurrency(currencyId: Long)
}