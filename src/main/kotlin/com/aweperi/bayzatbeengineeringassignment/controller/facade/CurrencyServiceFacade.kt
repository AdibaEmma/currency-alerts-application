package com.aweperi.bayzatbeengineeringassignment.controller.facade

import com.aweperi.bayzatbeengineeringassignment.dto.CurrencyRequest
import com.aweperi.bayzatbeengineeringassignment.dto.CurrencyResponse
import com.aweperi.bayzatbeengineeringassignment.mapper.currency.CurrencyRequestMapper
import com.aweperi.bayzatbeengineeringassignment.mapper.currency.CurrencyResponseMapper
import com.aweperi.bayzatbeengineeringassignment.service.CurrencyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.util.MultiValueMap

@Component
class CurrencyServiceFacade(@Autowired private val currencyRequestMapper: CurrencyRequestMapper,
                            @Autowired private val currencyResponseMapper: CurrencyResponseMapper,
                            @Autowired private val currencyService: CurrencyService
) {
    fun addCurrencyRequest(currencyRequest: CurrencyRequest): CurrencyResponse {
        val currency = currencyRequestMapper.transform(currencyRequest)
        return currencyResponseMapper.transform(currencyService.addCurrency(currency))
    }

    fun fetchCurrencies(): List<CurrencyResponse> {
        return currencyService.fetchCurrencies().map { currencyResponseMapper.transform(it) }
    }

    fun getCurrencyById(currencyId: Long): CurrencyResponse {
        return currencyResponseMapper.transform(currencyService.getCurrencyById(currencyId))
    }

    fun getBySymbol(symbol: String): CurrencyResponse {
        return currencyResponseMapper.transform(currencyService.getCurrencyBySymbol(symbol))
    }

    fun updateCurrency(currencyId: Long, updateRequest: Map<String, Any>) {
        currencyService.updateCurrency(currencyId, updateRequest)
    }

    fun disableCurrency(currencyId: Long): CurrencyResponse {
        return currencyResponseMapper.transform(currencyService.disableCurrency(currencyId))
    }

    fun deleteCurrency(currencyId: Long) {
        currencyService.deleteCurrency(currencyId)
    }
}