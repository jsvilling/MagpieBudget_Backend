package ch.jvi.budgetmanager.backend.core.event

import ch.jvi.budgetmanager.backend.api.command.bus.CommandBus
import ch.jvi.budgetmanager.backend.api.command.store.CommandStore
import ch.jvi.budgetmanager.backend.core.event.TransactionEvent.CreateTransactionEvent
import ch.jvi.budgetmanager.backend.domain.transaction.TransactionCommand.CreateTransactionCommand
import ch.jvi.budgetmanager.core.api.EventListener
import org.springframework.stereotype.Component

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
        commandBus.send(createTransactionCommand)
        commandStore.saveCreationCommand(createTransactionCommand)
    }
}