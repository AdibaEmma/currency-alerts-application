package com.aweperi.bayzatbeengineeringassignment.security

import com.aweperi.bayzatbeengineeringassignment.security.config.AppAuthenticationManager
import com.aweperi.bayzatbeengineeringassignment.security.config.SecurityProperties
import com.aweperi.bayzatbeengineeringassignment.security.filters.CustomAuthenticationFilter
import com.aweperi.bayzatbeengineeringassignment.security.filters.CustomAuthorizationFilter
import com.aweperi.bayzatbeengineeringassignment.service.UserServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.POST
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy.STATELESS
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig(
    val userDetailsService: UserServiceImpl,
    val securityProperties: SecurityProperties,
    val authenticationManager: AppAuthenticationManager,
) {
    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
        val customAuthenticationFilter = CustomAuthenticationFilter(authenticationManager, securityProperties)
        customAuthenticationFilter.setFilterProcessesUrl("/api/v1/login")
        return http
            .cors().and()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(STATELESS) // no sessions
            .and()
            .authorizeRequests()
            .antMatchers("/api/**").permitAll()
            .antMatchers("/error/**").permitAll()
            .antMatchers(POST, "/api/v1/login").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilter(customAuthenticationFilter)
            .addFilter(CustomAuthorizationFilter(authenticationManager, userDetailsService, securityProperties))
            .build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource = UrlBasedCorsConfigurationSource().also { cors ->
        CorsConfiguration().apply {
            allowedOrigins = listOf("*")
            allowedMethods = listOf("POST", "PUT", "DELETE", "GET", "OPTIONS", "HEAD")
            allowedHeaders = listOf(
                "Authorization",
                "Content-Type",
                "X-Requested-With",
                "Accept",
                "Origin",
                "Access-Control-Request-Method",
                "Access-Control-Request-Headers"
            )
            exposedHeaders = listOf(
                "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "Authorization", "Content-Disposition"
            )
            maxAge = 3600
            cors.registerCorsConfiguration("/**", this)
        }
    }
}