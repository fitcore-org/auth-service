package com.fitcore.auth.adapter.web.dto

data class PasswordResetRequestDTO(
    val email: String
)

data class PasswordResetConfirmDTO(
    val email: String,
    val code: String,
    val newPassword: String
)
