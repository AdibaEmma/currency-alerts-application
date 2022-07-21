package com.aweperi.bayzatbeengineeringassignment.service

import com.aweperi.bayzatbeengineeringassignment.dto.CurrencyRequest
import com.aweperi.bayzatbeengineeringassignment.error_handling.exceptions.CurrencyNotFoundException
import com.aweperi.bayzatbeengineeringassignment.error_handling.exceptions.DuplicateCurrencyException
import com.aweperi.bayzatbeengineeringassignment.error_handling.exceptions.IllegalCurrencyPriceException
import com.aweperi.bayzatbeengineeringassignment.model.Currency
import com.aweperi.bayzatbeengineeringassignment.repository.CurrencyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class CurrencyServiceImpl(@Autowired private val currencyRepository: CurrencyRepository) : CurrencyService {

    override fun addCurrency(currency: Currency): Currency {
        val foundCurrency = this.currencyRepository.findBySymbolOrName(currency.symbol, null)
        if (foundCurrency != null) throw DuplicateCurrencyException()

        return if (currency.currentPrice >= BigDecimal.ZERO) currencyRepository.save(currency) else
                throw IllegalCurrencyPriceException()
    }

    override fun fetchCurrencies(): List<Currency> {
        return this.currencyRepository.findAll().sortedBy { it.createdTime }
    }

    override fun getCurrencyById(currencyId: Long): Currency {
        return this.currencyRepository.findByIdOrNull(currencyId) ?: throw CurrencyNotFoundException()
    }

    override fun getCurrencyBySymbol(symbol: String): Currency {
        return this.currencyRepository.findBySymbolOrName(symbol, null) ?: throw CurrencyNotFoundException()
    }

    override fun updateCurrency(currencyId: Long, updateRequest: CurrencyRequest) {
        val foundCurrency = this.getCurrencyById(currencyId)
        foundCurrency.name = updateRequest.name
        foundCurrency.symbol = updateRequest.symbol
        foundCurrency.currentPrice = updateRequest.currentPrice
        foundCurrency.enabled = updateRequest.enabled

        currencyRepository.save(foundCurrency)
    }

    override fun disableCurrency(currencyId: Long): Currency {
        val foundCurrency = this.getCurrencyById(currencyId)
        foundCurrency.enabled = false
        return currencyRepository.save(foundCurrency)
    }

    override fun deleteCurrency(currencyId: Long) {
        this.getCurrencyById(currencyId)
        currencyRepository.deleteById(currencyId)
    }
}