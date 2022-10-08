package ch.jvi.magpie.service

import ch.jvi.magpie.core.api.Event
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class EventBusBusImpl(private val publisher: ApplicationEventPublisher) : EventBus {
    override fun send(event: Event) {
        publisher.publishEvent(event)
    }
}
