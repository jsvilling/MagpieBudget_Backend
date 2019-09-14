package ch.jvi.budgetmanager.backend.api.command

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
    fun save(command: Command)
}