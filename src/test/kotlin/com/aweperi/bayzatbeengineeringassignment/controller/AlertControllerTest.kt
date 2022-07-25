package com.aweperi.bayzatbeengineeringassignment.controller

import com.aweperi.bayzatbeengineeringassignment.model.Alert
import com.aweperi.bayzatbeengineeringassignment.model.AlertStatus
import com.aweperi.bayzatbeengineeringassignment.model.Currency
import com.aweperi.bayzatbeengineeringassignment.service.AlertService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.CapturingSlot
import io.mockk.every
import io.mockk.verify
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
internal class AlertControllerTest(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    lateinit var alertService: AlertService
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

    private val alertsWithADACurrencySymbol = listOf(
        Alert(
            cardona,
            BigDecimal.valueOf(1000.00),
            AlertStatus.CANCELED,
            LocalDateTime.now(),
            null,
            1
        )
    )

    @Test
    fun `should return list of related currencies as json`() {
        val currencySymbol = "ADA"

        every { alertService.getAlertsByCurrencySymbol(1, capture(symbolSlot)) } returns alertsWithADACurrencySymbol

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/1/alerts")
            .contentType(MediaType.APPLICATION_JSON)
            .param("currencySymbol", currencySymbol))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.payload").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.payload.size()", Matchers.`is`(1)))
            .andExpect(MockMvcResultMatchers.jsonPath("$.payload[0].currencySymbol").value(currencySymbol))
    }

    @Test
    fun `should create an alert`() {
        val currencySymbol = "ADA"
        every { alertService.createAlert (1,capture(symbolSlot), any()) } returns alertsWithADACurrencySymbol[0]

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/1/alerts")
            .contentType(MediaType.APPLICATION_JSON)
            .param("currencySymbol", currencySymbol)
            .content("{\"targetPrice\": 250.00, \"status\": \"NEW\"}"))
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("$").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.payload").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.payload.currencySymbol").value(currencySymbol))

        verify(exactly = 1) { alertService.createAlert (1,capture(symbolSlot), any()) }
    }
}