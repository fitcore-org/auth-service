package com.fitcore.auth.domain.port.`in`

import com.fitcore.auth.adapter.web.dto.LoginRequest
import com.fitcore.auth.adapter.web.dto.LoginResponse

import com.fitcore.auth.adapter.web.dto.RegisterRequest
import com.fitcore.auth.adapter.web.dto.RegisterResponse


interface AuthUseCase {
    fun login(request: LoginRequest): LoginResponse
    fun register(request: RegisterRequest): RegisterResponse
}

