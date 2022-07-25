package com.aweperi.bayzatbeengineeringassignment.controller

import com.aweperi.bayzatbeengineeringassignment.model.Currency
import com.aweperi.bayzatbeengineeringassignment.service.CurrencyServiceImpl
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
internal class CurrencyControllerTest(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    lateinit var currencyService: CurrencyServiceImpl

    private val currency = Currency(
            "Cardano",
            "ADA",
            BigDecimal.valueOf(200.0),
            true,
            LocalDateTime.now(),
            UUID.randomUUID().toString(),
            1
        )

    @Test
    fun `should return a currency as json`() {
        every { currencyService.getCurrencyById(1) } returns currency

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/currencies/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isFound)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$").exists())
            .andExpect(jsonPath("$.payload").exists())
            .andExpect(jsonPath("$.payload.symbol").value(currency.symbol))
            .andReturn()

        verify(exactly = 1) {
            currencyService.getCurrencyById(1)
        }
    }
}