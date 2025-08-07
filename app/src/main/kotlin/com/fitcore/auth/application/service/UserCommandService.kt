package com.fitcore.auth.application.service

import com.fitcore.auth.adapter.web.dto.UpdateUserRequest
import com.fitcore.auth.adapter.web.dto.UserResponse
import com.fitcore.auth.application.exception.EmailAlreadyRegisteredException
import com.fitcore.auth.domain.model.UserRole
import com.fitcore.auth.domain.port.`in`.UserCommandUseCase
import com.fitcore.auth.domain.port.out.UserRepositoryPort
import com.fitcore.auth.infrastructure.messaging.UserEventPublisher
import com.fitcore.auth.infrastructure.messaging.events.UserUpdatedEvent
import com.fitcore.auth.infrastructure.messaging.events.UserDeletedEvent
import org.springframework.stereotype.Service

@Service
class UserCommandService(
    private val userRepositoryPort: UserRepositoryPort,
    private val userEventPublisher: UserEventPublisher
) : UserCommandUseCase {
    override fun updateUser(id: Long, request: UpdateUserRequest): UserResponse {
        val user = userRepositoryPort.findById(id) ?: throw IllegalArgumentException("User not found")
        val newEmail = request.email ?: user.email
        val existingByEmail = userRepositoryPort.findByEmail(newEmail)
        if (existingByEmail != null && existingByEmail.id != id) {
            throw EmailAlreadyRegisteredException("E-mail already exists")
        }
        val newCpf = request.cpf ?: user.cpf
        if (newCpf != null && newCpf != user.cpf) {
            val existingByCpf = userRepositoryPort.findAll().find { it.cpf == newCpf && it.id != id }
            if (existingByCpf != null) {
                throw IllegalArgumentException("CPF already exists")
            }
        }
        val updated = user.copy(
            name = request.name ?: user.name,
            email = newEmail,
            role = request.role?.let { UserRole.valueOf(it) } ?: user.role,
            cpf = newCpf,
            birthDate = request.birthDate ?: user.birthDate,
            updatedAt = System.currentTimeMillis()
        )
        val saved = userRepositoryPort.save(updated)
        userEventPublisher.publishUserUpdated(
            UserUpdatedEvent(
                id = saved.id!!,
                name = saved.name,
                email = saved.email,
                role = saved.role.name,
                cpf = saved.cpf,
                birthDate = saved.birthDate
            )
        )
        return UserResponse.fromDomain(saved)
    }

    override fun deleteById(id: Long) {
        val user = userRepositoryPort.findById(id) ?: throw IllegalArgumentException("User not found")
        userEventPublisher.publishUserDeleted(
            UserDeletedEvent(
                id = user.id!!,
                name = user.name,
                email = user.email,
                role = user.role.name,
                cpf = user.cpf,
                birthDate = user.birthDate
            )
        )
        userRepositoryPort.deleteById(id)
    }
}
