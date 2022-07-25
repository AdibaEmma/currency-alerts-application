package com.aweperi.bayzatbeengineeringassignment.controller

import com.aweperi.bayzatbeengineeringassignment.controller.facade.AlertServiceFacade
import com.aweperi.bayzatbeengineeringassignment.dto.AlertRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("api/v1/users/{userId}/alerts")
@PreAuthorize("hasAnyAuthority('USER','ADMIN')")
@Validated
class AlertController(@Autowired private val alertServiceFacade: AlertServiceFacade) {
    @PostMapping
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    fun createAlert(@PathVariable("userId") userId: Long, @Valid @RequestBody alertRequest: AlertRequest, @RequestParam("currencySymbol") currencySymbol: String): ResponseEntity<*> {
        return ResponseHandler.handleResponseBody(HttpStatus.CREATED,"New alert created",
        alertServiceFacade.createAlert(userId, currencySymbol, alertRequest))
    }

    @GetMapping
    fun findAlertsByCurrencySymbol(@PathVariable("userId") userId: Long, @RequestParam("currencySymbol", required = false) currencySymbol: String?): ResponseEntity<*> {
        return ResponseHandler.handleResponseBody(HttpStatus.OK, "Fetch successful",
            alertServiceFacade.getAlerts(userId, currencySymbol))
    }

    @GetMapping("/{alertId}")
    fun getAlert(@PathVariable("userId") userId: Long, @PathVariable("alertId") alertId: Long): ResponseEntity<*> {
        return ResponseHandler.handleResponseBody(HttpStatus.OK, "Alert found",
            alertServiceFacade.getAlert(userId, alertId))
    }

    @PatchMapping("/{alertId}")
    fun updateAlert(@PathVariable("userId") userId: Long, @PathVariable("alertId") alertId: Long, @RequestBody updateRequest: Map<String, Any>): ResponseEntity<*> {
        return ResponseHandler.handleResponseBody(HttpStatus.OK, "Update successful",
            alertServiceFacade.updateAlert(userId, alertId, updateRequest))
    }

    @PutMapping("/{alertId}")
    fun toggleAlertStatus(@PathVariable("userId") userId: Long,@PathVariable("alertId") alertId: Long, @RequestBody updateRequest: Map<String, Any>): ResponseEntity<*> {
        return ResponseHandler.handleResponseBody(HttpStatus.OK, "Alert status changed",
            alertServiceFacade.toggleAlertStatus(userId, alertId, updateRequest))
    }

    @DeleteMapping("/{alertId}")
    fun deleteAlert(@PathVariable("userId") userId: Long, @PathVariable("alertId") alertId: Long): ResponseEntity<*> {
        return ResponseHandler.handleResponseBody(HttpStatus.OK, "Alert found",
            alertServiceFacade.deleteAlert(userId, alertId))
    }
}