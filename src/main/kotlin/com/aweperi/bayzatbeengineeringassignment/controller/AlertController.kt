package com.aweperi.bayzatbeengineeringassignment.controller

import com.aweperi.bayzatbeengineeringassignment.controller.facade.AlertServiceFacade
import com.aweperi.bayzatbeengineeringassignment.dto.AlertRequest
import com.aweperi.bayzatbeengineeringassignment.model.AlertStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("api/v1/alerts")
class AlertController(@Autowired private val alertServiceFacade: AlertServiceFacade) {
    @PostMapping
    fun createAlert(@Valid @RequestBody alertRequest: AlertRequest, @RequestParam("currencySymbol") currencySymbol: String): ResponseEntity<*> {
        return ResponseHandler.handleResponseBody(HttpStatus.CREATED,"New alert created",
        alertServiceFacade.createAlert(currencySymbol, alertRequest))
    }

    @GetMapping
    fun findAlertsByCurrencySymbol(@RequestParam("currencySymbol") currencySymbol: String): ResponseEntity<*> {
        return ResponseHandler.handleResponseBody(HttpStatus.OK, "Fetch successful",
            alertServiceFacade.getAlertsByCurrencySymbol(currencySymbol))
    }

    @GetMapping("/{alertId}")
    fun getAlert(@PathVariable("alertId") alertId: Long): ResponseEntity<*> {
        return ResponseHandler.handleResponseBody(HttpStatus.OK, "Alert found",
            alertServiceFacade.getAlert(alertId))
    }

    @PutMapping("/{alertId}")
    fun updateAlert(@PathVariable("alertId") alertId: Long, @RequestBody alertRequest: AlertRequest): ResponseEntity<*> {
        return ResponseHandler.handleResponseBody(HttpStatus.OK, "Update successful",
            alertServiceFacade.updateAlert(alertId, alertRequest))
    }

    @PatchMapping("/{alertId}")
    fun toggleAlertStatus(@PathVariable("alertId") alertId: Long, @RequestParam("status") status: AlertStatus): ResponseEntity<*> {
        return ResponseHandler.handleResponseBody(HttpStatus.OK, "Alert status changed",
            alertServiceFacade.toggleAlertStatus(alertId, status))
    }

    @DeleteMapping("/{alertId}")
    fun deleteAlert(@PathVariable("alertId") alertId: Long): ResponseEntity<*> {
        return ResponseHandler.handleResponseBody(HttpStatus.OK, "Alert found",
            alertServiceFacade.deleteAlert(alertId))
    }
}