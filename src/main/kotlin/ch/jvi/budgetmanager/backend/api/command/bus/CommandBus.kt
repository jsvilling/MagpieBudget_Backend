package ch.jvi.budgetmanager.backend.api.command.bus

import ch.jvi.budgetmanager.backend.api.command.Command

/**
 * Command Bus interface.
 *
 * Implementations of this interface are expected to implement a mechanism to send the received command to all
 * interested listeners. Listeners should be configured using the @CommandListener annotation.
 *
 * @author J. Villing
 */
interface CommandBus {

    /**
     * Send the passed commands over the CommandBus.
     */
    fun send(command: Command)

    /**
     * Send all passed commands over the CommandBus.
     *
     * The default implementation of this method sends all passed commands using CommandBus#send.
     */
    fun sendAll(commands: List<Command>) {
        commands.forEach(this::send)
    }
}