package com.aweperi.bayzatbeengineeringassignment.service

import com.aweperi.bayzatbeengineeringassignment.model.Alert
import com.aweperi.bayzatbeengineeringassignment.model.AlertStatus
import com.aweperi.bayzatbeengineeringassignment.model.Currency
import com.aweperi.bayzatbeengineeringassignment.repository.AlertRepository
import com.aweperi.bayzatbeengineeringassignment.repository.CurrencyRepository
import com.aweperi.bayzatbeengineeringassignment.repository.UserRepository
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
    private val userRepository = mockk<UserRepository>(relaxed = true)
    private val currencyRepository = mockk<CurrencyRepository>(relaxed = true)
    private val alertService = AlertServiceImpl(alertRepository, currencyRepository, userRepository)
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
            tether,
            BigDecimal.valueOf(250.00),
            AlertStatus.TRIGGERRED,
            LocalDateTime.now(),
            null,
            1
        ),
        Alert(
            cardona,
            BigDecimal.valueOf(1000.00),
            AlertStatus.CANCELED,
            LocalDateTime.now(),
            null,
            2
        ),
        Alert(
            tether,
            BigDecimal.valueOf(400.00),
            AlertStatus.NEW,
            LocalDateTime.now(),
            null,
            3
        )
    )

    @Test
    fun `should return list of alerts when call getAlerts`() {
        every {alertRepository.findAll() } returns alerts

        assertThat(alertService.getAlerts(, null)).allMatch { it.currency!!.symbol.isNotBlank() }
    }

    @Test
    fun `should return list of alerts with a given currency symbol`() {

        every { alertService.getAlertsByCurrencySymbol(, capture(symbolSlot)) } returns alerts

        assertThat(alertService.getAlertsByCurrencySymbol(, "ADA")).containsOnlyOnce(alerts[1])
    }
}