package com.aweperi.bayzatbeengineeringassignment.service

import com.aweperi.bayzatbeengineeringassignment.model.Alert
import com.aweperi.bayzatbeengineeringassignment.model.AlertStatus
import com.aweperi.bayzatbeengineeringassignment.model.Currency
import com.aweperi.bayzatbeengineeringassignment.repository.AlertRepository
import com.aweperi.bayzatbeengineeringassignment.repository.CurrencyRepository
import io.mockk.CapturingSlot
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

internal class AlertServiceImplTest {
    private val alertRepository = mockk<AlertRepository>(relaxed = true)
    private val currencyRepository = mockk<CurrencyRepository>(relaxed = true)
    private val alertService = AlertServiceImpl(alertRepository, currencyRepository)
    private var symbolSlot = CapturingSlot<String>()
    private val cardona = Currency(
        "Cardano",
        "ADA",
        BigDecimal.valueOf(200.0),
        true,
        LocalDateTime.now(),
        UUID.randomUUID().toString(),
        1
    )

    private val tether = Currency(
    "Tether",
    "USDT",
    BigDecimal.valueOf(100.0),
    true,
    LocalDateTime.now(),
    UUID.randomUUID().toString(),
    2
    )

    private val alerts = listOf(
        Alert(
            null,
            BigDecimal.valueOf(250.00),
            AlertStatus.TRIGGERRED,
            LocalDateTime.now(),
            1
        ),
        Alert(
            null,
            BigDecimal.valueOf(1000.00),
            AlertStatus.CANCELED,
            LocalDateTime.now(),
            2
        ),
        Alert(
            null,
            BigDecimal.valueOf(400.00),
            AlertStatus.NEW,
            LocalDateTime.now(),
            3
        )
    )

    @Test
    fun `should return list of alerts when call getAlerts`() {
        alerts[2].currency = tether
        alerts[1].currency = cardona
        alerts[0].currency = tether

        every {alertRepository.findAll() } returns alerts

        assertThat(alertService.getAlerts()).allMatch { it.currency!!.symbol.isNotBlank() }
    }

    @Test
    fun `should return list of alerts with a given currency symbol`() {
        alerts[2].currency = tether
        alerts[1].currency = cardona
        alerts[0].currency = tether

        every { alertService.getAlertsByCurrencySymbol(capture(symbolSlot)) } returns alerts

        assertThat(alertService.getAlertsByCurrencySymbol("ADA")).containsOnlyOnce(alerts[1])
    }
}