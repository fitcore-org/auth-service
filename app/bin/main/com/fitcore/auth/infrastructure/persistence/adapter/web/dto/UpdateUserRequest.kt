package com.fitcore.auth.adapter.web.dto

data class UpdateUserRequest(
    val name: String?,
    val email: String?,
    val role: String?,
    val cpf: String?,
    val birthDate: String?
)


