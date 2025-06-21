package com.fitcore.auth.adapter.web.controller

import com.fitcore.auth.adapter.web.dto.LoginRequest
import com.fitcore.auth.adapter.web.dto.LoginResponse
import com.fitcore.auth.adapter.web.dto.RegisterRequest
import com.fitcore.auth.adapter.web.dto.RegisterResponse
import com.fitcore.auth.application.service.TokenBlacklistService
import com.fitcore.auth.domain.port.`in`.AuthUseCase
import com.fitcore.auth.infrastructure.security.JwtUtil
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authUseCase: AuthUseCase,
    private val tokenBlacklistService: TokenBlacklistService
) {
    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<LoginResponse> {
        val response = authUseCase.login(request)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<RegisterResponse> {
        val response = authUseCase.register(request)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/logout")
    fun logout(@RequestHeader("Authorization") authorization: String): ResponseEntity<String> {
        val token = authorization.removePrefix("Bearer ").trim()
        val claims = JwtUtil.extractAllClaims(token)
        val exp = (claims["exp"] as? Number)?.toLong() ?: 0L
        tokenBlacklistService.blacklistToken(token, exp)
        return ResponseEntity.ok("Logout successful")
    }

}
