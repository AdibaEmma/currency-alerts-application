package com.aweperi.bayzatbeengineeringassignment.service

import com.aweperi.bayzatbeengineeringassignment.repository.UserRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
): UserDetailsService {
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(s: String): UserDetails {
        val user = userRepository.findByUsername(s)
            .orElseThrow { UsernameNotFoundException("The username $s doesn't exist") }

        val authorities = ArrayList<GrantedAuthority>()
        if (user.roles != null) {
            user.roles!!.forEach { authorities.add(SimpleGrantedAuthority(it.roleName)) }
        }
        return org.springframework.security.core.userdetails.User(
            user.username,
            user.password,
            authorities
        )
    }
}