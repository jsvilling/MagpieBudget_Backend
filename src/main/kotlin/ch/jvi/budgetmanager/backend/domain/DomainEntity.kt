package ch.jvi.budgetmanager.backend.domain

import ch.jvi.budgetmanager.backend.api.command.Command

/**
 * Interface for all domain entities
 *
 * @author J. Villing
 */
interface DomainEntity<T : Command> {
    fun applyAll(commands: List<T>) = commands.forEach { apply(it) }
    fun apply(command: T)
}