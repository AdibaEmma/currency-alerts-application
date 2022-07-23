package com.aweperi.bayzatbeengineeringassignment.repository

import com.aweperi.bayzatbeengineeringassignment.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository: JpaRepository<User, Long> {
    fun findUserByUsername(email: String): Optional<User>
}