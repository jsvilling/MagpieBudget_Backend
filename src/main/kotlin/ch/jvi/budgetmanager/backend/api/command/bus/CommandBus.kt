package ch.jvi.budgetmanager.backend.api.command.bus

import ch.jvi.budgetmanager.backend.api.command.Command

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