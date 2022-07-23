package com.aweperi.bayzatbeengineeringassignment.service

import com.aweperi.bayzatbeengineeringassignment.model.Role
import com.aweperi.bayzatbeengineeringassignment.model.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserServiceImpl: UserDetailsService, UserService {
    override fun loadUserByUsername(username: String?): UserDetails {
        TODO("Not yet implemented")
    }

    override fun userSignUp(user: User): User {
        TODO("Not yet implemented")
    }

    override fun getUsers(): List<User> {
        TODO("Not yet implemented")
    }

    override fun getUser(username: String): User {
        TODO("Not yet implemented")
    }

    override fun getUserRoles(username: String): List<Role> {
        TODO("Not yet implemented")
    }

    override fun updateUser(username: String, user: User) {
        TODO("Not yet implemented")
    }

    override fun updateUserRole(username: String, role: Role) {
        TODO("Not yet implemented")
    }

    override fun disableUser(username: String) {
        TODO("Not yet implemented")
    }

    override fun deleteUser(username: String) {
        TODO("Not yet implemented")
    }
}