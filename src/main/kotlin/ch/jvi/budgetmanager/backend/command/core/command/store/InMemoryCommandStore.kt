package ch.jvi.budgetmanager.backend.command.core.command.store

import ch.jvi.budgetmanager.backend.command.api.command.Command
import ch.jvi.budgetmanager.backend.command.api.command.CreationCommand
import ch.jvi.budgetmanager.backend.command.api.command.store.CommandStore
import ch.jvi.budgetmanager.backend.command.domain.account.AccountCommand
import ch.jvi.budgetmanager.backend.command.domain.transfer.TransferCommand
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import kotlin.streams.toList

/**
 * Fake implementation for an in memory command store
 *
 * @author J. Villing
 */
@Service
@Profile("integrationTest")
class InMemoryCommandStore : CommandStore {
    private val creationCommands: MutableSet<CreationCommand> = HashSet()
    private val commands: MutableSet<Command> = HashSet()

    override fun find(entityId: String): List<Command> {
        return commands.stream().filter { it.entityId == entityId }.toList()
    }

    override fun findAccountCommands(entityId: String): List<AccountCommand> {
        return commands.stream().filter { it is AccountCommand && it.entityId == entityId }.map { it as AccountCommand }
            .toList()
    }

    override fun findTransferCommands(entityId: String): List<TransferCommand> {
        return commands.stream().filter { it is TransferCommand && it.entityId == entityId }
            .map { it as TransferCommand }
            .toList()
    }

    override fun findCreationCommand(entityId: String): CreationCommand {
        return creationCommands.stream().filter { it.entityId == entityId }.findFirst().get()
    }

    override fun findCreationCommands(predicate: (CreationCommand) -> Boolean): List<CreationCommand> {
        return creationCommands.stream().filter(predicate).toList();
    }

    override fun save(command: Command) {
        commands.add(command)
    }

    override fun saveCreationCommand(command: CreationCommand) {
        creationCommands.add(command)
    }

}