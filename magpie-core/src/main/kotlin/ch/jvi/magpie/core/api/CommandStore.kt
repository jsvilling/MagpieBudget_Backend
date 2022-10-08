package ch.jvi.magpie.core.api

/**
 * A CommandStore is used to persist and retrieve commands.
 *
 * It provides entity specific methods for persisting and retrieving commands.
 *
 * Further it differentiates between Update- and CreationCommand. When commands are requested only UpdateCommands are returned.
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
interface CommandStore<C, U> {

    /**
     * @return All creation commands that belong to the account with the given entityId
     */
    fun findCreationCommand(entity: String): C

    /**
     * @return All creation commands of the given type C
     */
    fun findAllCreationCommands(): List<C>

    /**
     * @return All update commands that belong to the entity with the given entityId
     */
    fun findUpdateCommands(entityId: String): List<U>

    /**
     * Persists the given command.
     */
    fun save(command: U): U

    /**
     * Persists all given commands.
     */
    fun saveAll(commands: List<U>) = commands.forEach { this.save(it) }
}
