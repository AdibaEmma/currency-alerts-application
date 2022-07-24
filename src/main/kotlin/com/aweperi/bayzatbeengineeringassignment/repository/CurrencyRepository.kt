package com.aweperi.bayzatbeengineeringassignment.repository

import com.aweperi.bayzatbeengineeringassignment.model.Currency
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CurrencyRepository: JpaRepository<Currency, Long> {
    fun findBySymbol(symbol: String): Optional<Currency>
}