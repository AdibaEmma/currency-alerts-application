package com.aweperi.bayzatbeengineeringassignment.repository

import com.aweperi.bayzatbeengineeringassignment.model.Currency
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CurrencyRepository: JpaRepository<Currency, Long> {
}