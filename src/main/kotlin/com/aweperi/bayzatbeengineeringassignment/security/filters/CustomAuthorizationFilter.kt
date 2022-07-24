package com.aweperi.bayzatbeengineeringassignment.security.filters

import com.aweperi.bayzatbeengineeringassignment.security.config.SecurityProperties
import com.aweperi.bayzatbeengineeringassignment.service.UserServiceImpl
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomAuthorizationFilter(
    authManager: AuthenticationManager,
    private val userDetailsService: UserServiceImpl,
    private val securityProperties: SecurityProperties
) : BasicAuthenticationFilter(authManager) {
    val logger = LoggerFactory.getLogger(javaClass)
    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(
        req: HttpServletRequest,
        res: HttpServletResponse,
        chain: FilterChain
    ) {
        val header = req.getHeader(securityProperties.headerString)
        if (header == null || !header.startsWith(securityProperties.tokenPrefix)) {
            chain.doFilter(req, res)
            return
        }
        getAuthentication(header)?.also {
            SecurityContextHolder.getContext().authentication = it
        }
        chain.doFilter(req, res)
    }

    private fun getAuthentication(token: String): UsernamePasswordAuthenticationToken? {
        return try {
            val claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(securityProperties.secret.toByteArray()))
                .build()
                .parseClaimsJws(token.replace(securityProperties.tokenPrefix, ""))
            val userDetail = userDetailsService.loadUserByUsername(claims.body.subject)
            UsernamePasswordAuthenticationToken(claims.body.subject, null, userDetail.authorities)
        } catch (ex: Exception) {
            logger.error(ex.localizedMessage)
            return null
        }
    }
}