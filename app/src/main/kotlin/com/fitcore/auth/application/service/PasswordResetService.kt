package com.fitcore.auth.application.service

import com.fitcore.auth.adapter.web.dto.PasswordResetRequestDTO
import com.fitcore.auth.adapter.web.dto.PasswordResetConfirmDTO
import com.fitcore.auth.domain.port.out.UserRepositoryPort
import com.fitcore.auth.infrastructure.email.EmailSenderService
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap

@Service
class PasswordResetService(
    private val userRepository: UserRepositoryPort,
    private val emailSenderService: EmailSenderService
) {
    private val tokenStore = ConcurrentHashMap<String, ResetTokenData>()

    fun requestReset(request: PasswordResetRequestDTO) {
        val user = userRepository.findByEmail(request.email)
        if (user != null) {
            val code = (100_000..999_999).random().toString()
            val expiresAt = Instant.now().plusSeconds(900) // 15 minutes
            tokenStore[request.email] = ResetTokenData(code, expiresAt)
            val content = """
                <p>Hello,</p>
                <p>Your password reset code is: <b>$code</b></p>
                <p>This code will expire in 15 minutes.</p>
                <p>If you did not request this, please ignore this email.</p>
            """.trimIndent()
            emailSenderService.sendEmail(request.email, "Password Reset Code", content)
        }
    }

    fun confirmReset(request: PasswordResetConfirmDTO) {
        val data = tokenStore[request.email]
            ?: throw IllegalArgumentException("Invalid or expired code.")
        if (data.code != request.code || Instant.now().isAfter(data.expiresAt)) {
            throw IllegalArgumentException("Invalid or expired code.")
        }
        val user = userRepository.findByEmail(request.email)
            ?: throw IllegalArgumentException("User not found.")

        val newPasswordHash = BCrypt.hashpw(request.newPassword, BCrypt.gensalt())
        val updatedUser = user.copy(password = newPasswordHash)
        userRepository.save(updatedUser)
        tokenStore.remove(request.email)
    }

    private data class ResetTokenData(val code: String, val expiresAt: Instant)
}
