package com.fitcore.auth.application.service

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@Service
class TokenBlacklistService {
    private val blacklist = ConcurrentHashMap<String, Long>()

    fun blacklistToken(token: String, expiration: Long) {
        blacklist[token] = expiration
    }

    fun isTokenBlacklisted(token: String): Boolean {
        val exp = blacklist[token]
        val now = System.currentTimeMillis() / 1000
        if (exp != null && exp > now) {
            return true
        } else {
            if (exp != null) blacklist.remove(token)
            return false
        }
    }

    @Scheduled(fixedRate = 60_000)
    fun cleanExpiredTokens() {
        val now = System.currentTimeMillis() / 1000
        blacklist.entries.removeIf { it.value <= now }
    }
}
