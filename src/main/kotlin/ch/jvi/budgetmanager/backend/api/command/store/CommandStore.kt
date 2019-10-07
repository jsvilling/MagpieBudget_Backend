package ch.jvi.budgetmanager.backend.api.command.store

import ch.jvi.budgetmanager.backend.api.command.Command
import ch.jvi.budgetmanager.backend.api.command.CreationCommand
import ch.jvi.budgetmanager.backend.domain.account.AccountCommand
import ch.jvi.budgetmanager.backend.domain.transfer.TransferCommand

/**
 * A CommandStore is used to persist and retrieve commands.
 *
 * It provides entity specific methods for persisting and retrieving commands.
 *
 * Further it distincts between Update- and CreationCommand. When commands are requested only UpdateCommands are returned.
 * If on the other hand a creationCommand is requested only the first matching creation command is returned.
 *
 * There are no methods to find individual commands by their commandId. The commandId exists only for the purpose of
 * distinguishing between commands with the same domain data. Commands itself have to be immutable and will always be
 * identified by their corresponding entity.
 *
 * Commands may not be updated. If an entity changes a new Command indicating the changes must be persisted.
 *
 * Commands may only be deleted if the corresponding entity is deleted.
 *
 * All methods will throw a RuntimeException if no matching element is found. The exceptions are expected to be handled
 * by the caller.
 *
 * @author J. Villing
 *
 */
interface CommandStore {
    /**
     * @return All update commands that belong to the entity with the given entityId
     */
    fun find(entityId: String): List<Command>

    /**
     * @return All update commands that belong to the account with the given entityId
     */
    fun findAccountCommands(entityId: String): List<AccountCommand>

    /**
     * @return All update commands that belong to the transfer with the given entityId
     */
    fun findTransferCommands(entityId: String): List<TransferCommand>

    /**
     * @return The creation command that belongs to the entity with the given entityId
     */
    fun findCreationCommand(entityId: String): CreationCommand

    /**
     * Persists the given command.
     */
    fun save(command: Command)

    /**
     * Persists all given command.
     *
     * The default implementation of this method calls CommandStore#save for each passed command.
     */
    fun saveAll(commands: List<Command>) = commands.forEach(this::save)

    /**
     * Persists the given creation command.
     */
    fun saveCreationCommand(command: CreationCommand)
}