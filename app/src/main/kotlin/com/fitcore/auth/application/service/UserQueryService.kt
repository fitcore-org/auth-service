package com.fitcore.auth.application.service

import com.fitcore.auth.domain.model.User
import com.fitcore.auth.domain.port.`in`.UserQueryUseCase
import com.fitcore.auth.domain.port.out.UserRepositoryPort
import org.springframework.stereotype.Service

@Service
class UserQueryService(
    private val userRepositoryPort: UserRepositoryPort
) : UserQueryUseCase {
    override fun findAll(): List<User> {
        return userRepositoryPort.findAll()
    }
}
