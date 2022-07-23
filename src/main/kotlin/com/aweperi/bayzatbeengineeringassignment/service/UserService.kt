package com.aweperi.bayzatbeengineeringassignment.service

import com.aweperi.bayzatbeengineeringassignment.model.Role
import com.aweperi.bayzatbeengineeringassignment.model.User

interface UserService {
    fun userSignUp(user: User): User
    fun getUsers(): List<User>
    fun getUser(username: String): User
    fun getUserRoles(username: String): Set<Role>
    fun updateUser(username: String, userUpdateRequest: Map<String, Any>)
    fun updateUserRole(username: String, roleName: String)
    fun disableUser(username: String)
    fun deleteUser(username: String)
}