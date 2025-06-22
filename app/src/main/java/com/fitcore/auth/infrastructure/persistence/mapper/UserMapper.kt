package com.fitcore.auth.infrastructure.persistence.mapper

import com.fitcore.auth.domain.model.User
import com.fitcore.auth.domain.model.UserRole
import com.fitcore.auth.infrastructure.persistence.entity.UserEntity

object UserMapper {
    fun toDomain(entity: UserEntity): User = User(
        id = entity.id,
        name = entity.name,
        email = entity.email,
        password = entity.password,
        role = UserRole.valueOf(entity.role),
        createdAt = entity.createdAt,
        updatedAt = entity.updatedAt,
        cpf = entity.cpf,
        birthDate = entity.birthDate
    )

    fun toEntity(domain: User): UserEntity = UserEntity(
        id = domain.id,
        name = domain.name,
        email = domain.email,
        password = domain.password,
        role = domain.role.name,
        createdAt = domain.createdAt,
        updatedAt = domain.updatedAt,
        cpf = domain.cpf,
        birthDate = domain.birthDate
    )
}
