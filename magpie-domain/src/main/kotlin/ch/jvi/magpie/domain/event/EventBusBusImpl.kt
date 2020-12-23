package ch.jvi.magpie.domain.event

import ch.jvi.magpie.domain.event.api.Event
import ch.jvi.magpie.domain.event.api.EventBus
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class EventBusBusImpl(private val publisher: ApplicationEventPublisher) : EventBus {
    override fun send(event: Event) {
        publisher.publishEvent(event)
    }
}