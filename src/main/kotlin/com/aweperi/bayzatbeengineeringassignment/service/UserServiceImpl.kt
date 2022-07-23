package com.aweperi.bayzatbeengineeringassignment.service

import com.aweperi.bayzatbeengineeringassignment.error_handling.exceptions.DuplicateEmailException
import com.aweperi.bayzatbeengineeringassignment.error_handling.exceptions.UserNotFoundException
import com.aweperi.bayzatbeengineeringassignment.model.Role
import com.aweperi.bayzatbeengineeringassignment.model.User
import com.aweperi.bayzatbeengineeringassignment.repository.UserRepository
import com.aweperi.bayzatbeengineeringassignment.repository.UserRoleRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import java.util.function.BiConsumer

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val userRoleRepository: UserRoleRepository,
    private val bcryptPasswordEncoder: BCryptPasswordEncoder,
): UserDetailsService, UserService {
    override fun loadUserByUsername(email: String): UserDetails {
        return userRepository.findUserByUsername(email).orElseThrow { UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)) }
    }

    override fun userSignUp(user: User): User {
        return if(this.userRepository.findUserByUsername(user.username).isPresent) throw DuplicateEmailException()
                else {
            val encodedPassword: String = bcryptPasswordEncoder.encode(user.password)

            val userRole: Role = userRoleRepository.findRoleByName("USER")
            user.password = encodedPassword
            user.addRoleToUser(userRole)

            this.userRepository.save(user)
        }
    }

    override fun getUsers(): List<User> {
        return this.userRepository.findAll()
    }

    override fun getUser(username: String): User {
        return this.userRepository.findUserByUsername(username).orElseThrow { UserNotFoundException() }
    }

    override fun getUserRoles(username: String): Set<Role> {
        val user = this.getUser(username)
        return user.roles!!
    }

    override fun updateUser(username: String, userUpdateRequest: Map<String, Any>) {
        val foundUser = this.getUser(username)

        userUpdateRequest.forEach { _ ->
            BiConsumer { change: String?, value: Any? ->
                when (change) {
                    "firstName" -> foundUser.firstName = value as String
                    "lastName" -> foundUser.lastName = value as String
                    "email" -> foundUser.username = value as String
                    "password" -> foundUser.password = value as String
                }
            }
        }
            this.userRepository.save(foundUser)
    }

    override fun updateUserRole(username: String, roleName: String) {
        val user = this.getUser(username)
        val foundRole = this.userRoleRepository.findRoleByName(roleName.uppercase(Locale.getDefault()))
        user.roles = user.roles?.plus(foundRole)
    }

    override fun disableUser(username: String) {
        val user = this.getUser(username)
        user.toggleEnabled(false)
    }

    override fun deleteUser(username: String) {
        val user = this.getUser(username)
        this.userRepository.delete(user)
    }

    companion object {
        const val USER_NOT_FOUND_MSG = "user with email %s not found"
    }
}