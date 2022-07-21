package com.aweperi.bayzatbeengineeringassignment.service

import com.aweperi.bayzatbeengineeringassignment.dto.CurrencyRequest
import com.aweperi.bayzatbeengineeringassignment.model.Currency

interface CurrencyService {
    fun addCurrency(currency: Currency): Currency
    fun fetchCurrencies(): List<Currency>
    fun getCurrencyById(currencyId: Long): Currency
    fun getCurrencyBySymbol(symbol: String): Currency
    fun updateCurrency(currencyId: Long, updateRequest: CurrencyRequest)
    fun disableCurrency(currencyId: Long): Currency
    fun deleteCurrency(currencyId: Long)
}