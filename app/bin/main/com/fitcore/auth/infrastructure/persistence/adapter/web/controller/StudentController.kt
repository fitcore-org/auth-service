package com.fitcore.auth.adapter.web.controller

import com.fitcore.auth.adapter.web.dto.RegisterStudentRequest
import com.fitcore.auth.adapter.web.dto.UserResponse
import com.fitcore.auth.application.service.StudentService
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/students")
class StudentController(
    private val studentService: StudentService
) {
    private val logger = LoggerFactory.getLogger(StudentController::class.java)

    @PostMapping("/register")
    fun registerStudent(
        @RequestBody request: RegisterStudentRequest,
        @RequestHeader("X-User-Id") userId: String,
        @RequestHeader("X-User-Role") userRole: String
    ): ResponseEntity<UserResponse> {
        if (userRole.uppercase() == "TEACHER") {
            logger.warn("Access blocked: teacher attempted to register a student. id=$userId")
            throw ResponseStatusException(HttpStatus.FORBIDDEN, "Teachers are not allowed to register students.")
        }
        return ResponseEntity.ok(studentService.registerStudent(request))
    }
}
