package com.fitcore.auth.infrastructure.security

import com.fitcore.auth.application.service.TokenBlacklistService
import org.springframework.security.core.authority.SimpleGrantedAuthority
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthFilter(
    private val tokenBlacklistService: TokenBlacklistService
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")
        val token = authHeader?.takeIf { it.startsWith("Bearer ") }?.substring(7)

        if (!token.isNullOrBlank()) {
            if (tokenBlacklistService.isTokenBlacklisted(token)) {
                response.status = HttpServletResponse.SC_UNAUTHORIZED
                response.contentType = "application/json"
                response.writer.write("{\"error\": \"Invalid token. Please log in again.\"}")
                response.writer.flush()
                return
            }
            if (JwtUtil.isValid(token)) {
                val userId = JwtUtil.getUserIdFromToken(token)
                val role = JwtUtil.getRoleFromToken(token)
                val authorities = listOf(SimpleGrantedAuthority("ROLE_$role"))
                val userDetails = JwtUserDetails(userId, role, authorities)
                val authentication = UsernamePasswordAuthenticationToken(
                    userDetails, null, authorities
                )
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authentication
            }
        }
        filterChain.doFilter(request, response)
    }
}
