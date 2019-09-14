package ch.jvi.budgetmanager.backend.core.command

import ch.jvi.budgetmanager.backend.api.command.CommandBus
import ch.jvi.budgetmanager.backend.api.command.Command
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

/**
 * Implementation of the CommandBus
 *
 * All Commands are published via a ApplicationEventPublisher
 *
 * @author J. Villing
 */
@Component
class CommandBusImpl(private val publisher: ApplicationEventPublisher) : CommandBus {
    override fun send(command: Command) {
        publisher.publishEvent(command)
    }
}