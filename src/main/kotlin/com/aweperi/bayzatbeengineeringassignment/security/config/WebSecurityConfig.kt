package com.aweperi.bayzatbeengineeringassignment.security.config

import com.aweperi.bayzatbeengineeringassignment.security.filter.CustomAuthenticationFilter
import com.aweperi.bayzatbeengineeringassignment.security.filter.CustomAuthorizationFilter
import com.aweperi.bayzatbeengineeringassignment.service.UserServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig(
    private val userService: UserServiceImpl,
    private val bcryptPasswordEncoder: BCryptPasswordEncoder,
    val securityProperties: SecurityProperties,
) {

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        val customAuthenticationFilter = customAuthenticationFilter(http.getSharedObject(AuthenticationManagerBuilder::class.java)
            )
        customAuthenticationFilter.setFilterProcessesUrl("/api/v1/login")
        return http
                .cors().and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // no sessions
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/v*/signup/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v*/signup/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v*/login").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v*/login").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v*/users/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/api/v*/users/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v*/users/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v*/users/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v*/currencies/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/api/v*/currencies/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v*/currencies/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v*/currencies/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v*/alerts/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/api/v*/alerts/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v*/alerts/**").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v*/alerts/**").hasAnyAuthority("USER", "ADMIN")
                .anyRequest().authenticated()
                .and()
                .addFilter(customAuthenticationFilter)
                .addFilterBefore(CustomAuthorizationFilter(securityProperties), UsernamePasswordAuthenticationFilter::class.java)
                .build()
    }


    @Throws(Exception::class)
    @Autowired
    fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService<UserDetailsService?>(userService).passwordEncoder(bcryptPasswordEncoder)
    }

    fun customAuthenticationFilter(authMgrBuilder: AuthenticationManagerBuilder): CustomAuthenticationFilter {
        return CustomAuthenticationFilter(authMgrBuilder, securityProperties)
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