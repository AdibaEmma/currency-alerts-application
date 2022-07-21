package com.aweperi.bayzatbeengineeringassignment.service

import com.aweperi.bayzatbeengineeringassignment.model.Currency
import com.aweperi.bayzatbeengineeringassignment.repository.CurrencyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.util.Assert

@Service
class CurrencyServiceImpl(@Autowired private val currencyRepository: CurrencyRepository) : CurrencyService {

    override fun addCurrency(currency: Currency): Currency {
        TODO("Not yet implemented")
    }

    override fun fetchCurrencies(): List<Currency> {
        TODO("Not yet implemented")
    }

    override fun getCurrency(value: Any): Currency {
        TODO("Not yet implemented")
    }

    override fun updateCurrency(currencyId: Long) {
        TODO("Not yet implemented")
    }

    override fun toggleCurrencyStatus(currencyId: Long): Currency {
        TODO("Not yet implemented")
    }

    override fun deleteCurrency(currencyId: Long) {
        TODO("Not yet implemented")
    }

    fun findCurrencyById(currencyId: Long): Currency? =  this.currencyRepository.findByIdOrNull(currencyId)
}