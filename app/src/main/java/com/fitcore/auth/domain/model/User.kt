package com.fitcore.auth.domain.model

enum class UserRole {
    ADMIN,
    SECRETARY,
    TEACHER
}

data class User(
    val id: Long? = null,
    val name: String,
    val email: String,
    val password: String,
    val role: UserRole,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
