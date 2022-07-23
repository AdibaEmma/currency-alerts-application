package com.aweperi.bayzatbeengineeringassignment.security.config

import com.aweperi.bayzatbeengineeringassignment.security.filter.CustomAuthenticationFilter
import com.aweperi.bayzatbeengineeringassignment.security.filter.CustomAuthorizationFilter
import com.aweperi.bayzatbeengineeringassignment.service.UserServiceImpl
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled=true, prePostEnabled=true)
class WebSecurityConfig(
    private val userService: UserServiceImpl,
    private val bcryptPasswordEncoder: BCryptPasswordEncoder,
) {

    @Bean
    @Throws(Exception::class)
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        val customAuthenticationFilter = customAuthenticationFilter(http.getSharedObject(AuthenticationManagerBuilder::class.java))
        customAuthenticationFilter.setFilterProcessesUrl("/api/v1/login")
        http.csrf().disable()
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/v*/signup/**").permitAll()
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/v*/signup/**").permitAll()
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/v*/login").permitAll()
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/v*/login").permitAll()
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/v*/users/**").hasAnyAuthority("USER", "ADMIN")
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/v*/users/**").hasAnyAuthority("USER", "ADMIN")
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/v*/users/**").hasAnyAuthority("USER", "ADMIN")
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/v*/users/**").hasAnyAuthority("USER", "ADMIN")
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/v*/currencies/**").hasAnyAuthority("USER", "ADMIN")
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/v*/currencies/**").hasAuthority("ADMIN")
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/v*/currencies/**").hasAuthority("ADMIN")
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/v*/currencies/**").hasAuthority("ADMIN")
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/v*/alerts/**").hasAnyAuthority("USER", "ADMIN")
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/v*/alerts/**").hasAnyAuthority("USER", "ADMIN")
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/v*/alerts/**").hasAnyAuthority("USER", "ADMIN")
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/v*/alerts/**").hasAnyAuthority("USER", "ADMIN")
        http.authorizeRequests().anyRequest().authenticated()
        http.addFilter(customAuthenticationFilter)
        http.addFilterBefore(CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter::class.java)
        http.httpBasic(withDefaults())
        return http.build()
    }


    @Throws(Exception::class)
    @Autowired
    fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService<UserDetailsService?>(userService).passwordEncoder(bcryptPasswordEncoder)
    }

    fun customAuthenticationFilter(authMgrBuilder: AuthenticationManagerBuilder): CustomAuthenticationFilter {
        return CustomAuthenticationFilter(authMgrBuilder)
    }

}