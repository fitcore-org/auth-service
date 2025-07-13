package com.fitcore.auth.adapter.web.controller

import com.fitcore.auth.adapter.web.dto.RegisterStudentRequest
import com.fitcore.auth.adapter.web.dto.UserResponse
import com.fitcore.auth.application.service.StudentService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder

@RestController
@RequestMapping("/students")
class StudentController(
    private val studentService: StudentService
) {
    private val logger = LoggerFactory.getLogger(StudentController::class.java)

    @PreAuthorize("hasRole('ADMIN') or hasRole('SECRETARY')")
    @PostMapping("/register")
    fun registerStudent(@RequestBody request: RegisterStudentRequest): ResponseEntity<UserResponse> {
        val authentication = SecurityContextHolder.getContext().authentication
        val userDetails = authentication.principal as? com.fitcore.auth.infrastructure.security.JwtUserDetails
        if (userDetails?.role == "TEACHER") {
            logger.warn("Access blocked: teacher attempted to register a student. id=${userDetails.id}")
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "Teachers are not allowed to register students.")
        }
        return ResponseEntity.ok(studentService.registerStudent(request))
    }
}
