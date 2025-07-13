package com.fitcore.auth.application.service

import com.fitcore.auth.adapter.web.dto.RegisterStudentRequest
import com.fitcore.auth.adapter.web.dto.UserResponse
import com.fitcore.auth.domain.model.User
import com.fitcore.auth.domain.model.UserRole
import com.fitcore.auth.domain.port.out.UserRepositoryPort
import org.springframework.stereotype.Service

@Service
class StudentService(
    private val userRepositoryPort: UserRepositoryPort
) {
    fun registerStudent(request: RegisterStudentRequest): UserResponse {
        if (userRepositoryPort.findByEmail(request.email) != null) {
            throw IllegalArgumentException("E-mail already registered")
        }
        if (userRepositoryPort.findAll().any { it.cpf == request.cpf }) {
            throw IllegalArgumentException("CPF already registered")
        }
        val user = User(
            name = request.name,
            email = request.email,
            password = request.password,
            role = UserRole.STUDENT,
            cpf = request.cpf,
            birthDate = request.birthDate
        )
        val saved = userRepositoryPort.save(user)
        return UserResponse.fromDomain(saved)
    }
}
