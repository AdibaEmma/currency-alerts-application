package com.aweperi.bayzatbeengineeringassignment.service

import com.aweperi.bayzatbeengineeringassignment.error_handling.exceptions.CurrencyNotFoundException
import com.aweperi.bayzatbeengineeringassignment.error_handling.exceptions.DuplicateCurrencyException
import com.aweperi.bayzatbeengineeringassignment.error_handling.exceptions.IllegalCurrencyPriceException
import com.aweperi.bayzatbeengineeringassignment.error_handling.exceptions.UnsupportedCurrencyCreationException
import com.aweperi.bayzatbeengineeringassignment.model.Currency
import com.aweperi.bayzatbeengineeringassignment.repository.CurrencyRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class CurrencyServiceImpl(private val currencyRepository: CurrencyRepository) : CurrencyService {
    private val unsupportedCurrencies = listOf("ETH", "LTC", "ZKN", "MRD", "LPR", "GBZ")

    override fun addCurrency(currency: Currency): Currency {
        if(unsupportedCurrencies.contains(currency.symbol)) throw UnsupportedCurrencyCreationException()
        val foundCurrency = this.currencyRepository.findBySymbol(currency.symbol)
        if (foundCurrency.isPresent) throw DuplicateCurrencyException()

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
        return this.currencyRepository.findBySymbol(symbol).orElseThrow { CurrencyNotFoundException() }
    }

    override fun updateCurrency(currencyId: Long, updateRequest: Map<String, Any>) {
        val foundCurrency = this.getCurrencyById(currencyId)

        updateRequest.forEach { (change: String?, value: Any?) ->
            when (change) {
                "name" -> foundCurrency.name = value as String
                "symbol" -> foundCurrency.symbol = value as String
                "currentPrice" -> foundCurrency.currentPrice = value as BigDecimal
                "enabled" -> foundCurrency.enabled = value as Boolean
            }
        }
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