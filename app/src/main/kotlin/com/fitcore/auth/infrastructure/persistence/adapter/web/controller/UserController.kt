package com.fitcore.auth.adapter.web.controller

import com.fitcore.auth.domain.port.`in`.UserQueryUseCase
import com.fitcore.auth.domain.port.`in`.UserCommandUseCase
import com.fitcore.auth.domain.model.User
import com.fitcore.auth.adapter.web.dto.UpdateUserRequest
import com.fitcore.auth.adapter.web.dto.UserResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth/users")
class UserController(
    private val userQueryUseCase: UserQueryUseCase,
    private val userCommandUseCase: UserCommandUseCase
) {
    @GetMapping
    fun getAll(): List<UserResponse> {
        return userQueryUseCase.findAll().map { UserResponse.fromDomain(it) }
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: Long): ResponseEntity<Void> {
        userCommandUseCase.deleteById(id)
        return ResponseEntity.noContent().build()
    }
    
    @PutMapping("/{id}")
    fun updateUser(
        @PathVariable id: Long,
        @RequestBody request: UpdateUserRequest
    ): ResponseEntity<UserResponse> {
        val updated = userCommandUseCase.updateUser(id, request)
        return ResponseEntity.ok(updated)
    }
}
