package com.aweperi.bayzatbeengineeringassignment.security.filters

import com.aweperi.bayzatbeengineeringassignment.add
import com.aweperi.bayzatbeengineeringassignment.model.User
import com.aweperi.bayzatbeengineeringassignment.security.config.SecurityProperties
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.collections.ArrayList

class CustomAuthenticationFilter(
    private val authManager: AuthenticationManager,
    private val securityProperties: SecurityProperties
) : UsernamePasswordAuthenticationFilter() {

    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(
        req: HttpServletRequest,
        res: HttpServletResponse?
    ): Authentication {
        val logger = LoggerFactory.getLogger(javaClass)
        return try {
            val mapper = jacksonObjectMapper()

            val user = mapper.readValue<User>(req.inputStream)

            authManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    user.username,
                    user.password,
                    ArrayList()
                )
            )
        } catch (ex: IOException) {
            logger.error(ex.localizedMessage)
            throw AuthenticationServiceException(ex.localizedMessage)
        }
    }

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(
        req: HttpServletRequest,
        res: HttpServletResponse,
        chain: FilterChain?,
        auth: Authentication
    ) {
        val authClaims: MutableList<String> = mutableListOf()
        auth.authorities?.let { authorities ->
            authorities.forEach { claim -> authClaims.add(claim.toString()) }
        }
        val token = Jwts.builder()
            .setSubject(auth.principal as String)
            .claim("auth", authClaims)
            .setExpiration(Date().add(Calendar.DAY_OF_MONTH, securityProperties.expirationTime))
            .signWith(Keys.hmacShaKeyFor(securityProperties.secret.toByteArray()), SignatureAlgorithm.HS512)
            .compact()
        res.addHeader(securityProperties.headerString, securityProperties.tokenPrefix + token)
    }
}