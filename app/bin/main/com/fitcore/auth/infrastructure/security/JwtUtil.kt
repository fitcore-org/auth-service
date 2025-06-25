package com.fitcore.auth.infrastructure.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import java.util.*
import javax.crypto.SecretKey

object JwtUtil {
    private val secretKey: SecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256)
    private const val EXPIRATION_MS = 1000 * 60 * 60 * 6 // 6 h

    fun generateToken(userId: Long, role: String): String {
        val now = Date()
        val expiry = Date(now.time + EXPIRATION_MS)
        return Jwts.builder()
            .setSubject(userId.toString())
            .claim("role", role)
            .setIssuedAt(now)
            .setExpiration(expiry)
            .signWith(secretKey)
            .compact()
    }
    fun extractAllClaims(token: String): Map<String, Any> {
        val claims = Jwts.parser()
            .verifyWith(secretKey).build()
            .parseSignedClaims(token)
            .payload

        val map = mutableMapOf<String, Any>()
        map["sub"] = claims.subject
        map["role"] = claims["role"] as String
        map["iat"] = claims.issuedAt.time / 1000
        map["exp"] = claims.expiration.time / 1000
        
        return map
    }

    fun getUserIdFromToken(token: String): Long =
        Jwts.parser().verifyWith(secretKey).build()
            .parseSignedClaims(token).payload.subject.toLong()

    fun getRoleFromToken(token: String): String =
        Jwts.parser().verifyWith(secretKey).build()
            .parseSignedClaims(token).payload["role"] as String

    fun isValid(token: String): Boolean = try {
        Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token)
        true
    } catch (e: Exception) {
        false
    }

}
