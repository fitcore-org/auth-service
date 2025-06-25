package com.fitcore.auth.domain.model

enum class UserRole {
    ADMIN,
    SECRETARY,
    TEACHER,
    STUDENT
}

data class User(
    val id: Long? = null,
    val name: String,
    val email: String,
    val password: String,
    val role: UserRole,
    val cpf: String? = null,       
    val birthDate: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

