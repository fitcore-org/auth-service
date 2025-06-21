package com.fitcore.auth.infrastructure.security

import com.fitcore.auth.application.service.TokenBlacklistService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig(
    private val tokenBlacklistService: TokenBlacklistService
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests {
                it
                    .requestMatchers("/auth/**").permitAll()
                    .anyRequest().authenticated()
            }

            .addFilterBefore(
                JwtAuthFilter(tokenBlacklistService), 
                UsernamePasswordAuthenticationFilter::class.java
            )
        return http.build()
    }
}
