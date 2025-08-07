package com.fitcore.auth.adapter.web.dto

import com.fitcore.auth.domain.model.UserRole

data class RegisterResponse(
    val id: Long,
    val name: String,
    val email: String,
    val role: String,
)
