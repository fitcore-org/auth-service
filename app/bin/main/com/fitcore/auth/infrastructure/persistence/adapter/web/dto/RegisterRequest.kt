package com.fitcore.auth.adapter.web.dto

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val role: String,
    val cpf: String? = null,
    val birthDate: String? = null
)
