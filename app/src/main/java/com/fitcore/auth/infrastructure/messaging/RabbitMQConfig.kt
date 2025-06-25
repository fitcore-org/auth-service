package com.fitcore.auth.infrastructure.messaging

import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQConfig {
    companion object {
        const val USER_REGISTERED_QUEUE = "user.registered"
        const val USER_UPDATED_QUEUE = "user.updated"
        const val USER_DELETED_QUEUE = "user.deleted"
    }

    @Bean
    fun userRegisteredQueue(): Queue = Queue(USER_REGISTERED_QUEUE, true)

    @Bean
    fun userUpdatedQueue(): Queue = Queue(USER_UPDATED_QUEUE, true)

    @Bean
    fun userDeletedQueue(): Queue = Queue(USER_DELETED_QUEUE, true)

    @Bean
    fun rabbitTemplate(connectionFactory: ConnectionFactory): RabbitTemplate {
        val template = RabbitTemplate(connectionFactory)
        template.messageConverter = Jackson2JsonMessageConverter()
        return template
    }
}
