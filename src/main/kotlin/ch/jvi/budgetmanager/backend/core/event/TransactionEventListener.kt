package ch.jvi.budgetmanager.backend.core.event

import ch.jvi.budgetmanager.backend.api.command.bus.CommandBus
import ch.jvi.budgetmanager.backend.api.command.store.CommandStore
import ch.jvi.budgetmanager.backend.core.event.TransactionEvent.CreateTransactionEvent
import ch.jvi.budgetmanager.backend.core.event.TransactionEvent.UpdateTransactionEvent
import ch.jvi.budgetmanager.backend.domain.account.AccountCommand.AdjustAccountBalanceCommand
import ch.jvi.budgetmanager.backend.domain.transaction.TransactionCommand.CreateTransactionCommand
import ch.jvi.budgetmanager.backend.domain.transaction.TransactionCommand.UpdateTransactionCommand
import ch.jvi.budgetmanager.backend.domain.transaction.TransactionType
import ch.jvi.budgetmanager.core.api.EventListener
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class TransactionEventListener(
    private val commandBus: CommandBus,
    private val commandStore: CommandStore
) {
    @EventListener
    fun handle(createTransactionEvent: CreateTransactionEvent) {
        val createTransactionCommand = CreateTransactionCommand(
            name = createTransactionEvent.name,
            amount = createTransactionEvent.amount,
            accountId = createTransactionEvent.accountId,
            type = createTransactionEvent.type
        )

        val adjustAccountBalanceCommand = AdjustAccountBalanceCommand(
            balanceChange = getUpdateAmount(createTransactionEvent.amount, createTransactionEvent.type),
            entityId = createTransactionEvent.accountId
        )

        commandBus.sendAll(listOf(createTransactionCommand, adjustAccountBalanceCommand))
        commandStore.saveCreationCommand(createTransactionCommand)
        commandStore.save(adjustAccountBalanceCommand)
    }

    @EventListener
    fun handle(updateTransactionEvent: UpdateTransactionEvent) {
        val updateTransactionCommand = UpdateTransactionCommand(
            entityId = updateTransactionEvent.transactionId,
            name = updateTransactionEvent.newName,
            amount = updateTransactionEvent.newAmount,
            accountId = updateTransactionEvent.newAccountId,
            type = updateTransactionEvent.newType
        )

        val adjustOldAccountBalanceCommand = AdjustAccountBalanceCommand(
            entityId = updateTransactionEvent.oldAccountId,
            balanceChange = updateTransactionEvent.oldAmount.negate()
        )

        val adjustNewAccountBalanceCommand = AdjustAccountBalanceCommand(
            entityId = updateTransactionEvent.newAccountId,
            balanceChange = getUpdateAmount(updateTransactionEvent.newAmount, updateTransactionEvent.newType)
        )

        val commands = listOf(updateTransactionCommand, adjustOldAccountBalanceCommand, adjustNewAccountBalanceCommand)
        commandBus.sendAll(commands)
        commandStore.saveAll(commands)
    }

    private fun getUpdateAmount(amount: BigDecimal, type: TransactionType): BigDecimal {
        if (type == TransactionType.EXPENSE) {
            return amount.negate()
        }
        return amount
    }
}