package com.fitcore.auth.domain.port.`in`

import com.fitcore.auth.domain.model.User

interface UserQueryUseCase {
    fun findAll(): List<User>
}
