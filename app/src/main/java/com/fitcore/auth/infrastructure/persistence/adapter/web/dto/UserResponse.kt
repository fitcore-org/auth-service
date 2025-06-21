package com.fitcore.auth.adapter.web.dto

import com.fitcore.auth.domain.model.User

data class UserResponse(
    val id: Long?,
    val name: String,
    val email: String,
    val role: String,
    val createdAt: Long,
    val updatedAt: Long
) {
    companion object {
        fun fromDomain(user: User) = UserResponse(
            id = user.id,
            name = user.name,
            email = user.email,
            role = user.role.name,
            createdAt = user.createdAt,
            updatedAt = user.updatedAt
        )
    }
}
