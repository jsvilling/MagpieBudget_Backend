package ch.jvi.budgetmanager.backend.core.message

import ch.jvi.budgetmanager.backend.api.message.Message
import ch.jvi.budgetmanager.backend.api.message.MessageBus
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class MessageBusBusImpl(private val publisher: ApplicationEventPublisher): MessageBus {
    override fun send(message: Message) {
        publisher.publishEvent(message)
    }
}