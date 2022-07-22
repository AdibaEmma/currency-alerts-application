package com.aweperi.bayzatbeengineeringassignment.error_handling

import com.aweperi.bayzatbeengineeringassignment.error_handling.exceptions.*
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
class CustomErrorHandler : ResponseEntityExceptionHandler() {
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

    @ExceptionHandler(CurrencyNotFoundException::class)
    fun handleCurrencyNotFoundException(ex: CurrencyNotFoundException, request: WebRequest): ErrorMessage {
        logger.error(ex.localizedMessage)
        return ErrorMessage(HttpStatus.NOT_FOUND.value(), Date(), ex.localizedMessage, request.getDescription(false))
    }

    @ExceptionHandler(AlertNotFoundException::class)
    fun handleAlertNotFoundException(ex: CurrencyNotFoundException, request: WebRequest): ResponseEntity<ErrorMessage> {
        logger.error(ex.localizedMessage)
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ErrorMessage(HttpStatus.NOT_FOUND.value(), Date(),
            ex.localizedMessage, request.getDescription(false)))
    }

    @ExceptionHandler(DuplicateCurrencyException::class)
    fun handleDuplicateCurrencyException(ex: DuplicateCurrencyException, request: WebRequest): ErrorMessage {
        logger.error(ex.localizedMessage)
        return ErrorMessage(HttpStatus.CONFLICT.value(), Date(),
            ex.localizedMessage, request.getDescription(false))
    }

    @ExceptionHandler(IllegalCurrencyPriceException::class)
    fun handleIllegalCurrencyPriceException(ex: IllegalCurrencyPriceException, request: WebRequest): ErrorMessage {
        logger.error(ex.localizedMessage)
        return ErrorMessage(HttpStatus.BAD_REQUEST.value(), Date(),
            ex.localizedMessage, request.getDescription(false))
    }

    @ExceptionHandler(UnsupportedCurrencyCreationException::class)
    fun handleUnsupportedCurrencyCreationException(ex: UnsupportedCurrencyCreationException, request: WebRequest): ErrorMessage {
        logger.error(ex.localizedMessage)
        return ErrorMessage(HttpStatus.BAD_REQUEST.value(), Date(),
            ex.localizedMessage, request.getDescription(false))
    }

    @ExceptionHandler(ClassNotFoundException::class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    fun classNotFoundException(ex: ClassNotFoundException, request: WebRequest): ErrorMessage {
        logger.error(ex.localizedMessage)
        return ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
            Date(), ex.localizedMessage, request.getDescription(false))
    }
}