package ch.jvi.magpie.eventbus

import ch.jvi.magpie.commandservice.EventBus
import ch.jvi.magpie.domain.Event
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class EventBusBusImpl(private val publisher: ApplicationEventPublisher) : EventBus {
    override fun send(event: Event) {
        publisher.publishEvent(event)
    }
}