package ch.jvi.budgetmanager.backend.core.event

import ch.jvi.budgetmanager.backend.api.command.bus.CommandBus
import ch.jvi.budgetmanager.backend.api.command.store.CommandStore
import ch.jvi.budgetmanager.backend.core.event.TransactionEvent.CreateTransactionEvent
import ch.jvi.budgetmanager.backend.domain.account.AccountCommand.AdjustAccountBalanceCommand
import ch.jvi.budgetmanager.backend.domain.transaction.TransactionCommand.CreateTransactionCommand
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

    private fun getUpdateAmount(amount: BigDecimal, type: TransactionType): BigDecimal {
        if (type == TransactionType.EXPENSE) {
            return amount.negate()
        }
        return amount
    }
}