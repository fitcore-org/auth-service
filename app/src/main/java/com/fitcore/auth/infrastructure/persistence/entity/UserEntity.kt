package com.fitcore.auth.infrastructure.persistence.entity

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class UserEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val role: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) {
    constructor() : this(null, "", "", "", "", System.currentTimeMillis(), System.currentTimeMillis())
    fun toDomain() = com.fitcore.auth.domain.model.User(
        id = id,
        name = name,
        email = email,
        password = password,
        role = com.fitcore.auth.domain.model.UserRole.valueOf(role),
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
