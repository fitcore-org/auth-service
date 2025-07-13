package com.fitcore.auth.application.service

import com.fitcore.auth.infrastructure.security.JwtUtil
import com.fitcore.auth.adapter.web.dto.LoginRequest
import com.fitcore.auth.adapter.web.dto.LoginResponse
import com.fitcore.auth.adapter.web.dto.RegisterRequest
import com.fitcore.auth.adapter.web.dto.RegisterResponse
import com.fitcore.auth.application.exception.EmailAlreadyRegisteredException
import com.fitcore.auth.domain.model.User
import com.fitcore.auth.domain.model.UserRole
import com.fitcore.auth.domain.port.`in`.AuthUseCase
import com.fitcore.auth.domain.port.out.UserRepositoryPort
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepositoryPort: UserRepositoryPort,
    private val passwordEncoder: PasswordEncoder
) : AuthUseCase {

    override fun login(request: LoginRequest): LoginResponse {
        val user = userRepositoryPort.findByEmail(request.email)
            ?: throw IllegalArgumentException("User not found")

        if (!passwordEncoder.matches(request.password, user.password)) {
            throw IllegalArgumentException("Invalid password")
        }

        val token = JwtUtil.generateToken(user.id!!, user.role.name)
        return LoginResponse(
            token = token,
            id = user.id!!,
            name = user.name,
            email = user.email,
            role = user.role.name
        )
    }

    override fun register(request: RegisterRequest): RegisterResponse {
        if (userRepositoryPort.findByEmail(request.email) != null) {
            throw EmailAlreadyRegisteredException("E-mail already exists")
        }
        val user = User(
            name = request.name,
            email = request.email,
            password = passwordEncoder.encode(request.password),
            role = UserRole.valueOf(request.role),
            cpf = request.cpf,
            birthDate = request.birthDate
        )
        val saved = userRepositoryPort.save(user)
        return RegisterResponse(
            id = saved.id!!,
            name = saved.name,
            email = saved.email,
            role = saved.role.name
        )
    }
}