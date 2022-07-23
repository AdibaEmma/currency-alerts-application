package com.aweperi.bayzatbeengineeringassignment.service

import com.aweperi.bayzatbeengineeringassignment.model.Role
import com.aweperi.bayzatbeengineeringassignment.model.User

interface UserService {
    fun userSignUp(user: User): User
    fun getUsers(): List<User>
    fun getUser(username: String): User
    fun getUserRoles(username: String): List<Role>
    fun updateUser(username: String, user: User)
    fun updateUserRole(username: String, role: Role)
    fun disableUser(username: String)
    fun deleteUser(username: String)
}