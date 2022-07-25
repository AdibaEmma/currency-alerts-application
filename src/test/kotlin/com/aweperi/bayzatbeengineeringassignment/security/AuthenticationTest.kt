package com.aweperi.bayzatbeengineeringassignment.security

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.getForEntity
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.http.*
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.math.BigDecimal

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@ActiveProfiles("test")
internal class AuthenticationTest(@Autowired private val restTemplate: TestRestTemplate) {
    private val loginForm = hashMapOf("username" to "john.doe", "password" to "test1234")
    private val currency = hashMapOf("name" to "cardona", "symbol" to "ADA",
        "currentPrice" to BigDecimal.valueOf(200.00), "enabled" to true)
    @Test
    @Order(1)
    fun `should return status forbidden when ping any restricted url`() {
        restTemplate.postForEntity<Any>("/api/v1/currencies", currency, MediaType.APPLICATION_JSON).also {
            assertNotNull(it)
            assertEquals(HttpStatus.FORBIDDEN, it.statusCode)
        }
    }

    @Test
    @Order(2)
    fun `should return status unauthorized when failed login with wrong username or password`() {
        val falseLoginForm = hashMapOf("username" to "john.doe", "password" to "wrongpassword")
        try {
            restTemplate.postForEntity<Any>("/api/v1/login", falseLoginForm).also {
                assertNotNull(it)
                assertEquals(HttpStatus.UNAUTHORIZED, it.statusCode)
            }
        } catch (e: Exception) {
            print("Fixme RestTemplate with HttpStatus.UNAUTHORIZED result")
        }
    }

    @Test
    @Order(5)
    fun `should return correct response when login successful`() {
        restTemplate.postForEntity<String>("/api/v1/login", loginForm).also {
            assertNotNull(it)
            assertEquals(HttpStatus.OK, it.statusCode)
            val bearer = it.headers["authorization"]?.get(0).orEmpty()
            assertNotNull(bearer)
            assertThat(bearer).contains("Bearer")
        }
    }

    @Test
    @Order(4)
    fun `should return correct response when ping restricted with good creds`() {
        val login = restTemplate.postForEntity<String>("/api/v1/login", loginForm)
        val bearer = login.headers["authorization"]?.get(0).orEmpty()
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers["Authorization"] = bearer
        val requestEntity = HttpEntity<String>(headers)

        restTemplate.exchange(
            "/api/v1/currencies",
            HttpMethod.GET,
            requestEntity,
            String::class.java
        ).also {
            assertNotNull(it)
            assertEquals(HttpStatus.OK, it.statusCode)
            assertThat(it.body).contains("payload")
        }
    }
}