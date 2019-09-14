package ch.jvi.budgetmanager.backend.server

import ch.jvi.budgetmanager.backend.api.command.Command
import ch.jvi.budgetmanager.backend.api.command.CommandStore
import org.springframework.stereotype.Service

/**
 * Fake implementation for an in memory command store
 *
 * @author J. Villing
 */
@Service
class CommandStoreFake : CommandStore {

    private val commands: MutableSet<Command> = HashSet()

    override fun save(command: Command) {
        commands.add(command)
    }
}