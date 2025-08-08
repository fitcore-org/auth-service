package com.fitcore.auth.infrastructure.seed

import com.fitcore.auth.domain.model.User
import com.fitcore.auth.domain.model.UserRole
import com.fitcore.auth.domain.port.out.UserRepositoryPort
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Profile
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class UserDataSeeder(
    private val userRepository: UserRepositoryPort,
    private val passwordEncoder: PasswordEncoder
) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {

        if (userRepository.findAll().isNotEmpty()) return

        val users = listOf(
            User(
                name = "Maria da Silva",
                email = "cleaner@example.com",
                password = passwordEncoder.encode("password123"),
                role = UserRole.CLEANER
            ),
            User(
                name = "Carlos P. Trainer",
                email = "trainer@example.com",
                password = passwordEncoder.encode("password123"),
                role = UserRole.PERSONAL_TRAINER
            ),
            User(
                name = "Ana Recepcionista",
                email = "receptionist@example.com",
                password = passwordEncoder.encode("password123"),
                role = UserRole.RECEPTIONIST
            ),
            User(
                name = "Victor Conde",
                email = "fitcore@fitcore.com",
                password = passwordEncoder.encode("password123"),
                role = UserRole.STUDENT,
                cpf = "12345678900",
                birthDate = "2000-04-15"
            ),
            User(
                name = "Fernanda Gerente",
                email = "manager@example.com",
                password = passwordEncoder.encode("password123"),
                role = UserRole.MANAGER
            )
        )

        users.forEach { userRepository.save(it) }

        println("[DEV SEED] Usuários de exemplo criados no banco.")
    }
}
