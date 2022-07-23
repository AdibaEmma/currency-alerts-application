package com.aweperi.bayzatbeengineeringassignment.security.filter

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.aweperi.bayzatbeengineeringassignment.model.User
import com.aweperi.bayzatbeengineeringassignment.security.config.AuthConfig
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.SmartInitializingSingleton
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Component
import java.io.IOException
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomAuthenticationFilter(private val authMgrBuilder: AuthenticationManagerBuilder):
    UsernamePasswordAuthenticationFilter(), SmartInitializingSingleton {
    private val logger = LoggerFactory.getLogger(javaClass)
    lateinit var authManager: AuthenticationManager
    private var jwtSecret: String = AuthConfig.getSecret()

    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        val username = request.getParameter("username")
        val password = request.getParameter("password")
        logger.info("Username is: $username")
        logger.info("Password is: $password")
        val authenticationToken = UsernamePasswordAuthenticationToken(username, password)
        return authManager.authenticate(authenticationToken)
    }

    @Throws(IOException::class)
    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authentication: Authentication,
    ) {
        val user: User = authentication.principal as User
        val algorithm: Algorithm = Algorithm.HMAC256(jwtSecret.toByteArray())
        val accessToken: String = JWT.create()
            .withSubject(user.username)
            .withExpiresAt(Date(System.currentTimeMillis() + 10 * 60 * 1000))
            .withIssuer(request.requestURL.toString())
            .withClaim("roles", user.authorities.stream().map { obj: GrantedAuthority -> obj.authority }.toList())
            .sign(algorithm)
        val refreshToken: String = JWT.create()
            .withSubject(user.username)
            .withExpiresAt(Date(System.currentTimeMillis() + 30 * 60 * 1000))
            .withIssuer(request.requestURL.toString())
            .sign(algorithm)
        //        response.setHeader("access_token", access_token);
//        response.setHeader("refresh_token", refresh_token);
        val tokens: MutableMap<String, String> = HashMap()
        tokens["access_token"] = accessToken
        tokens["refresh_token"] = refreshToken
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        ObjectMapper().writeValue(response.outputStream, tokens)
    }

    override fun setAuthenticationManager(authenticationManager: AuthenticationManager) {
        super.setAuthenticationManager(authenticationManager)
    }

    override fun afterSingletonsInstantiated() {
        this.authManager = authMgrBuilder.`object`
    }

}


