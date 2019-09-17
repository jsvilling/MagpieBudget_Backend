package ch.jvi.budgetmanager.backend.api.message

/**
 * Interface for a MessageBus implementation
 *
 * @author J. Villing
 */
interface MessageBus {

    fun send(message: Message)

    fun sendAll(messages: List<Message>) {
        messages.forEach(this::send)
    }
}