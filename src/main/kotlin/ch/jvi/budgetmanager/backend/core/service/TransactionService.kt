package ch.jvi.budgetmanager.backend.core.service

import ch.jvi.budgetmanager.backend.api.command.CreationCommand
import ch.jvi.budgetmanager.backend.api.command.store.CommandStore
import ch.jvi.budgetmanager.backend.api.event.EventBus
import ch.jvi.budgetmanager.backend.api.service.EntityService
import ch.jvi.budgetmanager.backend.core.event.TransactionEvent.CreateTransactionEvent
import ch.jvi.budgetmanager.backend.domain.transaction.Transaction
import ch.jvi.budgetmanager.backend.domain.transaction.TransactionCommand
import ch.jvi.budgetmanager.backend.domain.transaction.TransactionCommand.CreateTransactionCommand
import ch.jvi.budgetmanager.backend.domain.transaction.TransactionType
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class TransactionService(
    private val commandStore: CommandStore,
    private val eventBus: EventBus
) : EntityService<Transaction> {

    override fun find(entityId: String): Transaction {
        val createTransactionCommand = commandStore.findCreationCommand(entityId) as CreateTransactionCommand
        val transaction = Transaction(createTransactionCommand)
        return applyCommands(transaction)
    }

    override fun findAll(): List<Transaction> {
        return commandStore.findCreationCommands(this::isTransactionCreationCommand)
            .map { Transaction(it as CreateTransactionCommand) }
            .map { applyCommands(it) }
    }

    private fun isTransactionCreationCommand(command: CreationCommand): Boolean {
        return command is CreateTransactionCommand
    }

    private fun applyCommands(Transaction: Transaction): Transaction {
        val commands: List<TransactionCommand> = commandStore.findTransactionCommands(Transaction.id)
        Transaction.applyAll(commands)
        return Transaction
    }

    fun createTransaction(name: String, amount: BigDecimal, type: TransactionType) {
        val createTransactionEvent = CreateTransactionEvent(
            name = name,
            amount = amount,
            type = type
        )
        eventBus.send(createTransactionEvent);
    }

}