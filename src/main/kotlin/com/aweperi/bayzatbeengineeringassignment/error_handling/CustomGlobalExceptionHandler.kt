package com.aweperi.bayzatbeengineeringassignment.error_handling

import com.aweperi.bayzatbeengineeringassignment.error_handling.exceptions.CurrencyNotFoundException
import com.aweperi.bayzatbeengineeringassignment.error_handling.exceptions.DuplicateCurrencyException
import com.aweperi.bayzatbeengineeringassignment.error_handling.exceptions.IllegalCurrencyPriceException
import com.aweperi.bayzatbeengineeringassignment.error_handling.exceptions.UnsupportedCurrencyCreationException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.*
import java.util.stream.Collectors

@RestControllerAdvice
class CustomGlobalExceptionHandler : ResponseEntityExceptionHandler() {
    val logger = LoggerFactory.getLogger(javaClass)
    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest,
    ): ResponseEntity<Any> {
        val body: MutableMap<String, Any> = LinkedHashMap()
        body["timestamp"] = Date()
        body["status"] = status.value()

        //Get all errors
        val errors = ex.bindingResult
            .fieldErrors
            .stream()
            .map { obj: FieldError -> obj.defaultMessage }
            .collect(Collectors.toList())
        body["errors"] = errors
        return ResponseEntity(body, headers, status)
    }

    @ExceptionHandler(value = [CurrencyNotFoundException::class])
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    fun handleCurrencyNotFoundException(ex: CurrencyNotFoundException): ErrorMessage {
        logger.error(ex.message)
        return ErrorMessage(HttpStatus.NOT_FOUND.value(), Date(), "Currency Not Found Error", ex.message)
    }

    @ExceptionHandler(value = [DuplicateCurrencyException::class])
    @ResponseStatus(value = HttpStatus.CONFLICT)
    fun handleDuplicateCurrencyException(ex: DuplicateCurrencyException): ErrorMessage {
        logger.error(ex.message)
        return ErrorMessage(HttpStatus.CONFLICT.value(), Date(),
            "Currency Exists Error", ex.message)
    }

    @ExceptionHandler(value = [IllegalCurrencyPriceException::class])
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    fun handleIllegalCurrencyPriceException(ex: IllegalCurrencyPriceException): ErrorMessage {
        logger.error(ex.message)
        return ErrorMessage(HttpStatus.BAD_REQUEST.value(), Date(),
            "Currency Price Error", ex.message)
    }

    @ExceptionHandler(value = [UnsupportedCurrencyCreationException::class])
    @ResponseStatus(value = HttpStatus.CONFLICT)
    fun handleUnsupportedCurrencyCreationException(ex: UnsupportedCurrencyCreationException): ErrorMessage {
        logger.error(ex.message)
        return ErrorMessage(HttpStatus.NOT_FOUND.value(), Date(),
            "Currency Creation Error", ex.message)
    }

    @ExceptionHandler(value = [ClassNotFoundException::class])
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    fun classNotFoundException(ex: ClassNotFoundException): ErrorMessage {
        return ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
            Date(),
            "An unknown error occurred",
            ex.message)
    }
}