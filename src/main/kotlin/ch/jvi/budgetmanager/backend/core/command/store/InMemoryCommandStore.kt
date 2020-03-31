package ch.jvi.budgetmanager.backend.core.command.store

import ch.jvi.budgetmanager.backend.api.command.Command
import ch.jvi.budgetmanager.backend.api.command.CreationCommand
import ch.jvi.budgetmanager.backend.api.command.store.CommandStore
import ch.jvi.budgetmanager.backend.domain.account.AccountCommand
import ch.jvi.budgetmanager.backend.domain.budget.BudgetCommand
import ch.jvi.budgetmanager.backend.domain.transaction.TransactionCommand
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

    override fun findBudgetCommands(entityId: String): List<BudgetCommand> {
        return find(entityId).filterIsInstance<BudgetCommand>()
    }

    override fun findTransactionCommands(entityId: String): List<TransactionCommand> {
        return find(entityId).filterIsInstance<TransactionCommand>()
    }

    override fun findCreationCommand(entityId: String): CreationCommand {
        return creationCommands.stream().filter { it.entityId == entityId }.findFirst().orElseThrow()
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