package ch.jvi.budgetmanager.backend.domain.api

import ch.jvi.budgetmanager.domain.api.Command

/**
 * Command Bus interface
 *
 * @author J. Villing
 */
interface CommandBus {
    fun send(command: Command)

    fun sendAll(commands: List<Command>) {
        commands.forEach(this::send)
    }
}