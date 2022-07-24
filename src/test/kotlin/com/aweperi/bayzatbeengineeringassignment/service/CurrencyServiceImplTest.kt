package com.aweperi.bayzatbeengineeringassignment.service

import com.aweperi.bayzatbeengineeringassignment.model.Currency
import com.aweperi.bayzatbeengineeringassignment.repository.CurrencyRepository
import io.mockk.CapturingSlot
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

internal class CurrencyServiceImplTest {
    private val currencyRepository = mockk<CurrencyRepository>(relaxed = true)
    private val currencyService = CurrencyServiceImpl(currencyRepository)
    private var symbolSlot = CapturingSlot<String>()

    private val currencies = listOf(
        Currency(
            "Cardano",
            "ADA",
            BigDecimal.valueOf(200.0),
            true,
            LocalDateTime.now(),
            UUID.randomUUID().toString(),
            1
        ),
        Currency(
            "Tether",
            "USDT",
            BigDecimal.valueOf(100.0),
            true,
            LocalDateTime.now(),
            UUID.randomUUID().toString(),
            2
        )
    )

    @Test
    fun `should return list of currencies when call fetchCurrencies`() {
        every {currencyRepository.findAll() } returns currencies

        assertThat(currencyService.fetchCurrencies()).allMatch { it.name.isNotBlank() }
        assertThat(currencyService.fetchCurrencies()).allMatch { it.symbol.isNotBlank() }

    }

    @Test
    fun `should return a currency with a given currency symbol`() {

        every { currencyService.getCurrencyBySymbol(capture(symbolSlot)) } returns currencies.first()

        assertThat(currencyService.getCurrencyBySymbol("ADA")).isNotNull
    }
}