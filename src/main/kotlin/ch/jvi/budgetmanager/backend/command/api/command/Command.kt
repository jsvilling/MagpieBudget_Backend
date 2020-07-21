package ch.jvi.budgetmanager.backend.command.api.command

/**
 * Interface for a Command.
 *
 * Each command has a unique id entityId marked by the entityId property and belongs to exactly one entity marked by
 * the commandId property.
 *
 * @author J. Villing
 */
interface Command {
    val entityId: String
    val commandId: String
}