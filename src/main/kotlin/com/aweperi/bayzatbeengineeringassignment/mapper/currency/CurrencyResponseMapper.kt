package com.aweperi.bayzatbeengineeringassignment.mapper.currency

import com.aweperi.bayzatbeengineeringassignment.dto.CurrencyResponse
import com.aweperi.bayzatbeengineeringassignment.mapper.Mapper
import com.aweperi.bayzatbeengineeringassignment.model.Currency
import org.springframework.stereotype.Component

@Component
class CurrencyResponseMapper: Mapper<Currency, CurrencyResponse> {
    override fun transform(source: Currency): CurrencyResponse {
        return CurrencyResponse(
            source.currencyId,
            source.uuid.toString(),
            source.name,
            source.symbol,
            source.currentPrice,
            source.enabled,
            source.createdTime
        )
    }
}