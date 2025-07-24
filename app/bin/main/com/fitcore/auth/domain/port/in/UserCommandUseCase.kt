package com.fitcore.auth.domain.port.`in`

import com.fitcore.auth.adapter.web.dto.UpdateUserRequest
import com.fitcore.auth.adapter.web.dto.UserResponse

interface UserCommandUseCase {
    fun updateUser(id: Long, request: UpdateUserRequest): UserResponse
    fun deleteById(id: Long)
}
