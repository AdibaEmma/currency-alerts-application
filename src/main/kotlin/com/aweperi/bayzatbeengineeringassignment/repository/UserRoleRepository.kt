package com.aweperi.bayzatbeengineeringassignment.repository

import com.aweperi.bayzatbeengineeringassignment.model.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRoleRepository: JpaRepository<Role, Long> {
    fun findRoleByName(name: String): Role
}