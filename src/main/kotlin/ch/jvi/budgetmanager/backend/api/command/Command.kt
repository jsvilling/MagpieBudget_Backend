package ch.jvi.budgetmanager.backend.api.command

/**
 * @author J. Villing
 */
interface Command {
    val entityId: String
    val commandId: String
}