package ch.jvi.budgetmanager.backend.server

import ch.jvi.budgetmanager.backend.api.command.Command
import ch.jvi.budgetmanager.backend.api.command.CommandStore
import ch.jvi.budgetmanager.backend.api.command.CreationCommand
import org.springframework.stereotype.Service

/**
 * Fake implementation for an in memory command store
 *
 * @author J. Villing
 */
@Service
class CommandStoreFake : CommandStore {

    private val creationCommands: MutableSet<CreationCommand> = HashSet()
    private val commands: MutableSet<Command> = HashSet()

    override fun save(command: Command) {
        commands.add(command)
    }

    override fun saveCreationCommand(command: CreationCommand) {
        creationCommands.add(command)
    }

    override fun findCreationCommand(id: String): CreationCommand {
        return creationCommands.stream().filter {it.id == id}.findFirst().orElseThrow()
    }
}