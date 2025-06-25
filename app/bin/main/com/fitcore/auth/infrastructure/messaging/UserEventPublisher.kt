package com.fitcore.auth.infrastructure.messaging

import com.fitcore.auth.infrastructure.messaging.events.UserRegisteredEvent
import com.fitcore.auth.infrastructure.messaging.events.UserUpdatedEvent
import com.fitcore.auth.infrastructure.messaging.events.UserDeletedEvent
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class UserEventPublisher(
    private val rabbitTemplate: RabbitTemplate
) {
    fun publishUserRegistered(event: UserRegisteredEvent) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.USER_REGISTERED_QUEUE, event)
    }
    fun publishUserUpdated(event: UserUpdatedEvent) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.USER_UPDATED_QUEUE, event)
    }
    fun publishUserDeleted(event: UserDeletedEvent) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.USER_DELETED_QUEUE, event)
    }
}
