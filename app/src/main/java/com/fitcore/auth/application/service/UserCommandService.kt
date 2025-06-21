package com.fitcore.auth.application.service

import com.fitcore.auth.adapter.web.dto.UpdateUserRequest
import com.fitcore.auth.adapter.web.dto.UserResponse
import com.fitcore.auth.application.exception.EmailAlreadyRegisteredException
import com.fitcore.auth.domain.model.UserRole
import com.fitcore.auth.domain.port.`in`.UserCommandUseCase
import com.fitcore.auth.domain.port.out.UserRepositoryPort
import com.fitcore.auth.infrastructure.persistence.mapper.UserMapper
import org.springframework.stereotype.Service

@Service
class UserCommandService(
    private val userRepositoryPort: UserRepositoryPort
) : UserCommandUseCase {
    override fun updateUser(id: Long, request: UpdateUserRequest): UserResponse {

        val user = userRepositoryPort.findById(id) ?: throw IllegalArgumentException("User not found")
        val newEmail = request.email ?: user.email
        val existingByEmail = userRepositoryPort.findByEmail(newEmail)
        if (existingByEmail != null && existingByEmail.id != id) {
            throw EmailAlreadyRegisteredException("E-mail already exists")
        }

        val updated = user.copy(
            name = request.name ?: user.name,
            email = newEmail,
            role = request.role?.let { UserRole.valueOf(it) } ?: user.role,
            updatedAt = System.currentTimeMillis()
        )
        println("User a ser salvo: id=${updated.id}, email=${updated.email}, name=${updated.name}")
        val saved = userRepositoryPort.save(updated)
        return UserResponse.fromDomain(saved)
    }
}
