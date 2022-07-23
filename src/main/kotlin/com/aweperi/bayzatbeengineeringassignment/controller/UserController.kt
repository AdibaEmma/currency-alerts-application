package com.aweperi.bayzatbeengineeringassignment.controller

import com.aweperi.bayzatbeengineeringassignment.controller.facade.UserServiceFacade
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(private val userServiceFacade: UserServiceFacade) {
}