package com.aweperi.bayzatbeengineeringassignment.controller

import com.aweperi.bayzatbeengineeringassignment.controller.facade.CurrencyServiceFacade
import com.aweperi.bayzatbeengineeringassignment.dto.CurrencyRequest
import com.aweperi.bayzatbeengineeringassignment.dto.CurrencyResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("api/v1/currencies")
class CurrencyController(@Autowired private val currencyServiceFacade: CurrencyServiceFacade) {
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    fun addCurrency(@Valid @RequestBody currencyRequest: CurrencyRequest): ResponseEntity<*> {
        return ResponseHandler.handleResponseBody(HttpStatus.CREATED, "New currency added",
            currencyServiceFacade.addCurrencyRequest(currencyRequest))
    }

    @GetMapping
    fun getAllCurrencies(): ResponseEntity<*> {
        return ResponseHandler.handleResponseBody(HttpStatus.OK,"Fetch successful",
            currencyServiceFacade.fetchCurrencies())
    }

    @GetMapping("/{currencyId}")
    fun getCurrencyById(@PathVariable("currencyId") currencyId: Long): ResponseEntity<*> {
        return ResponseHandler.handleResponseBody(HttpStatus.FOUND,"Currency found",
            currencyServiceFacade.getCurrencyById(currencyId))
    }

    @GetMapping("/currency-by")
    fun getCurrencyBySymbol(@RequestParam("symbol") symbol: String): ResponseEntity<*> {
            return ResponseHandler.handleResponseBody(HttpStatus.FOUND,"Currency found",
                currencyServiceFacade.getBySymbol(symbol))
    }

    @PutMapping("/{currencyId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    fun updateCurrency(@PathVariable("currencyId") currencyId: Long,
                        @Valid @RequestBody updateRequest: CurrencyRequest): ResponseEntity<*> {
        return ResponseHandler.handleResponseBody(HttpStatus.FOUND,"Currency update successful",
            currencyServiceFacade.updateCurrency(currencyId, updateRequest))
    }

    @PatchMapping("/{currencyId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    fun disableCurrency(@PathVariable("currencyId") currencyId: Long): ResponseEntity<*> {
        return ResponseHandler.handleResponseBody(HttpStatus.FOUND,"Currency disabled",
            currencyServiceFacade.disableCurrency(currencyId))
    }

    @DeleteMapping("/{currencyId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    fun deleteCurrency(@PathVariable("currencyId") currencyId: Long): ResponseEntity<*> {
        return ResponseHandler.handleResponseBody(HttpStatus.OK,"Currency deleted",
            currencyServiceFacade.deleteCurrency(currencyId))
    }
}