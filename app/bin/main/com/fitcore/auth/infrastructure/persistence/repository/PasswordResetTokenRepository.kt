package com.fitcore.auth.infrastructure.persistence.repository

import com.fitcore.auth.infrastructure.persistence.entity.PasswordResetTokenEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PasswordResetTokenRepository : JpaRepository<PasswordResetTokenEntity, Long> {
    fun findByEmailAndCode(email: String, code: String): PasswordResetTokenEntity?
    fun deleteByEmail(email: String)
}
