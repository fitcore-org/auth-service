package com.fitcore.auth.infrastructure.messaging.events

import java.io.Serializable

data class UserDeletedEvent(
    val id: Long,
    val name: String,
    val email: String,
    val role: String,
    val cpf: String?,
    val birthDate: String?
) : Serializable
