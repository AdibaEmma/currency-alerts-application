package com.aweperi.bayzatbeengineeringassignment.mapper.currency

import com.aweperi.bayzatbeengineeringassignment.dto.CurrencyRequest
import com.aweperi.bayzatbeengineeringassignment.mapper.Mapper
import com.aweperi.bayzatbeengineeringassignment.model.Currency
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*

@Component
class CurrencyRequestMapper: Mapper<CurrencyRequest, Currency> {
    override fun transform(source: CurrencyRequest): Currency {
        return Currency(
            source.name,
            source.symbol,
            source.currentPrice,
            source.enabled,
            LocalDateTime.now(),
            UUID.randomUUID().toString(),
            null,
        )
    }

}