package com.aweperi.bayzatbeengineeringassignment.security.filter

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.aweperi.bayzatbeengineeringassignment.security.config.SecurityProperties
import com.benasher44.uuid.Uuid
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CustomAuthorizationFilter(private val securityProperties: SecurityProperties) : OncePerRequestFilter() {
    private val logger = LoggerFactory.getLogger(javaClass)

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        securityProperties.secret = Uuid.randomUUID().toString()

        if (request.servletPath == "/api/v1/login") {
            filterChain.doFilter(request, response)
        } else {
            val authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
            if (authorizationHeader != null && authorizationHeader.startsWith(securityProperties.tokenPrefix)) {
                try {
                    val token = authorizationHeader.substring(securityProperties.tokenPrefix.length)
                    val algorithm = Algorithm.HMAC256(securityProperties.secret.toByteArray())
                    val verifier = JWT.require(algorithm).build()
                    val decodedJWT = verifier.verify(token)
                    val username = decodedJWT.subject
                    val roles = decodedJWT.getClaim("roles").asArray(String::class.java)
                    val authorities: MutableCollection<SimpleGrantedAuthority> = ArrayList()
                    Arrays.stream(roles).forEach { role: String? ->
                        authorities.add(SimpleGrantedAuthority(role))
                    }
                    val authenticationToken = UsernamePasswordAuthenticationToken(username, null, authorities)
                    SecurityContextHolder.getContext().authentication = authenticationToken
                    filterChain.doFilter(request, response)
                } catch (ex: Exception) {
                    logger.error(ex.message)
                    response.setHeader("error", ex.message)
                    response.status = HttpStatus.UNAUTHORIZED.value()
                    val error: MutableMap<String, String?> = HashMap()
                    error["error_message"] = ex.message
                    response.contentType = MediaType.APPLICATION_JSON_VALUE
                    ObjectMapper().writeValue(response.outputStream, error)
                }
            } else {
                filterChain.doFilter(request, response)
            }
        }
    }
}