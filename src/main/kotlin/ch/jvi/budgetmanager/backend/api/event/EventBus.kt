package ch.jvi.budgetmanager.backend.api.event

/**
 * Interface for a EventeBus implementation
 *
 * @author J. Villing
 */
interface EventBus {

    fun send(event: Event)

    fun sendAll(events: List<Event>) {
        events.forEach(this::send)
    }
}