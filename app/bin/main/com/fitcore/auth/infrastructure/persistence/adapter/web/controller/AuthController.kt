package com.fitcore.auth.adapter.web.controller

import com.fitcore.auth.adapter.web.dto.*
import com.fitcore.auth.application.service.PasswordResetService
import com.fitcore.auth.application.service.TokenBlacklistService
import com.fitcore.auth.domain.port.`in`.AuthUseCase
import com.fitcore.auth.infrastructure.security.JwtUtil
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authUseCase: AuthUseCase,
    private val tokenBlacklistService: TokenBlacklistService,
    private val passwordResetService: PasswordResetService
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

    @PostMapping("/password-reset/request")
    fun passwordResetRequest(@RequestBody request: PasswordResetRequestDTO): ResponseEntity<String> {
        passwordResetService.requestReset(request)
        return ResponseEntity.ok("If the email exists, a code will be sent.")
    }

    @PostMapping("/password-reset/confirm")
    fun passwordResetConfirm(@RequestBody request: PasswordResetConfirmDTO): ResponseEntity<String> {
        passwordResetService.confirmReset(request)
        return ResponseEntity.ok("Password changed successfully.")
    }

}
