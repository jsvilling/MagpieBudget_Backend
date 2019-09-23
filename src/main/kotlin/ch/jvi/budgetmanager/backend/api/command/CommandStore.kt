package ch.jvi.budgetmanager.backend.api.command

import ch.jvi.budgetmanager.backend.domain.account.AccountCommand
import ch.jvi.budgetmanager.backend.domain.transfer.TransferCommand

/**
 * A CommandStore is used to persist and retrieve commands.
 *
 * Currently the CommandStore only provides the means to persist a single command as this is all that is needed in
 * the current state of the application.
 *
 * The CommandStore will be extended to enable retrieving commands and saving multiple commands in the futre.
 *
 * @author J. Villing
 *
 */
interface CommandStore {
    fun find(id: String): List<Command>
    fun findAccountCommands(id: String): List<AccountCommand>
    fun findTransferCommands(id: String): List<TransferCommand>
    fun findCreationCommand(id: String): CreationCommand
    fun save(command: Command)
    fun saveAll(commands: List<Command>) = commands.forEach(this::save)
    fun saveCreationCommand(command: CreationCommand)
}