package com.fitcore.auth.adapter.web.dto

data class RegisterStudentRequest(
    val name: String,
    val email: String,
    val password: String,
    val cpf: String,
    val birthDate: String 
)