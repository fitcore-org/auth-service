package com.fitcore.auth.infrastructure.persistence.entity

import jakarta.persistence.*

@Entity
@Table(name = "password_reset_tokens")
data class PasswordResetTokenEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val email: String,
    val code: String,
    val expiresAt: Long
)
