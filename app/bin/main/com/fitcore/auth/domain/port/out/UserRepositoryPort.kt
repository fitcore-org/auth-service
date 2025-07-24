package com.fitcore.auth.domain.port.out

import com.fitcore.auth.domain.model.User

interface UserRepositoryPort {
    fun save(user: User): User
    fun findByEmail(email: String): User?
    fun findById(id: Long): User?
    fun findAll(): List<User>
    fun deleteById(id: Long)
}
