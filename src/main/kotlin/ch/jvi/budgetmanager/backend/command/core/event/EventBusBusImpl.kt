package ch.jvi.budgetmanager.backend.command.core.event

import ch.jvi.budgetmanager.backend.command.api.event.Event
import ch.jvi.budgetmanager.backend.command.api.event.EventBus
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class EventBusBusImpl(private val publisher: ApplicationEventPublisher) : EventBus {
    override fun send(event: Event) {
        publisher.publishEvent(event)
    }
}