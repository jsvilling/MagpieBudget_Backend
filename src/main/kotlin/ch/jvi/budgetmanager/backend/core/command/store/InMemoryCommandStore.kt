package ch.jvi.budgetmanager.backend.core.command.store

import ch.jvi.budgetmanager.backend.api.command.Command
import ch.jvi.budgetmanager.backend.api.command.CreationCommand
import ch.jvi.budgetmanager.backend.api.command.store.CommandStore
import ch.jvi.budgetmanager.backend.domain.account.AccountCommand
import ch.jvi.budgetmanager.backend.domain.transfer.TransferCommand
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

    override fun find(id: String): List<Command> {
        return commands.stream().filter { it.entityId == id }.toList()
    }

    override fun findAccountCommands(id: String): List<AccountCommand> {
        return commands.stream().filter { it is AccountCommand && it.entityId == id }.map { it as AccountCommand }
            .toList()
    }

    override fun findTransferCommands(id: String): List<TransferCommand> {
        return commands.stream().filter { it is TransferCommand && it.entityId == id }.map { it as TransferCommand }
            .toList()
    }

    override fun findCreationCommand(id: String): CreationCommand {
        return creationCommands.stream().filter { it.entityId == id }.findFirst().orElseThrow()
    }

    override fun save(command: Command) {
        commands.add(command)
    }

    override fun saveCreationCommand(command: CreationCommand) {
        creationCommands.add(command)
    }

}