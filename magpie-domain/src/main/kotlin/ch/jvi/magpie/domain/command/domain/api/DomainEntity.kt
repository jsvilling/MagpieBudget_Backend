package ch.jvi.magpie.domain.command.domain.api

import ch.jvi.magpie.domain.command.api.Command

/**
 * Interface for all domain entities
 *
 * @author J. Villing
 */
interface DomainEntity<T : Command> {

    /**
     * This applies all given commands to the domain entity and updates all value fields accordingly.
     *
     * The default implementation of this method calls DomainEntity#apply for each given command.
     */
    fun applyAll(commands: List<T>) = commands.forEach { apply(it) }

    /**
     * This applies the given command to the domain entity and updates all value fields accordingly.
     */
    fun apply(command: T)
}