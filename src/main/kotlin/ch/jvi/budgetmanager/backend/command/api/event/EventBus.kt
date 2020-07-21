package ch.jvi.budgetmanager.backend.command.api.event

/**
 * Interface for a EventeBus implementation
 *
 * Implementations of this interface are expected to implement a mechanism to send the received event to all
 * interested listeners. Listeners should be configured using the @EventListener annotation.
 *
 * @author J. Villing
 */
interface EventBus {

    fun send(event: Event)

    fun sendAll(events: List<Event>) {
        events.forEach(this::send)
    }
}