package com.aweperi.bayzatbeengineeringassignment.controller.facade

import com.aweperi.bayzatbeengineeringassignment.service.AlertService
import org.springframework.beans.factory.annotation.Autowired

class AlertServiceFacade(@Autowired private val alertService: AlertService) {
}