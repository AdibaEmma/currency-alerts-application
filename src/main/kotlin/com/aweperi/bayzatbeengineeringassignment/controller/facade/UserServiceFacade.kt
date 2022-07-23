package com.aweperi.bayzatbeengineeringassignment.controller.facade

import com.aweperi.bayzatbeengineeringassignment.service.UserService
import org.springframework.stereotype.Component

@Component
class UserServiceFacade(private val userService: UserService) {
}