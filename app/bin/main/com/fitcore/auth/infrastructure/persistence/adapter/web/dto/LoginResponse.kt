package com.fitcore.auth.adapter.web.dto

data class LoginResponse(
    val token: String,
    val id: Long,
    val name: String,
    val email: String,
    val role: String
)
