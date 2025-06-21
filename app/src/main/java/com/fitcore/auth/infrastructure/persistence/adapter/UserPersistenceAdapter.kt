package com.fitcore.auth.infrastructure.persistence.adapter

import com.fitcore.auth.infrastructure.persistence.mapper.UserMapper
import com.fitcore.auth.domain.model.User
import com.fitcore.auth.domain.model.UserRole
import com.fitcore.auth.domain.port.out.UserRepositoryPort
import com.fitcore.auth.infrastructure.persistence.entity.UserEntity
import com.fitcore.auth.infrastructure.persistence.repository.UserJpaRepository
import org.springframework.stereotype.Component

@Component
class UserPersistenceAdapter(
    private val userJpaRepository: UserJpaRepository
) : UserRepositoryPort {

    override fun findByEmail(email: String): User? {
        return userJpaRepository.findByEmail(email)?.toDomain()
    }

    override fun findById(id: Long): User? {
        return userJpaRepository.findById(id).orElse(null)?.toDomain()
    }


    override fun findAll(): List<User> {
        return userJpaRepository.findAll().map { UserMapper.toDomain(it) }
    }

    override fun save(user: User): User {
        val entity = UserEntity(
            id = user.id,
            name = user.name,
            email = user.email,
            password = user.password,
            role = user.role.name,
            createdAt = user.createdAt,
            updatedAt = user.updatedAt
        )
        val saved = userJpaRepository.save(entity)
        return saved.toDomain()
    }
}
