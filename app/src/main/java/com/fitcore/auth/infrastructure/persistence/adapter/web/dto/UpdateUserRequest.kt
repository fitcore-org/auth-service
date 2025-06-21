package com.fitcore.auth.adapter.web.dto

data class UpdateUserRequest(
    val name: String? = null,
    val email: String? = null,
    val role: String? = null
)

