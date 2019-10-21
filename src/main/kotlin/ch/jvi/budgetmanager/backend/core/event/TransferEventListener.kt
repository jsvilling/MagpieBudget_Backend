package ch.jvi.budgetmanager.backend.core.event

import ch.jvi.budgetmanager.backend.api.command.bus.CommandBus
import ch.jvi.budgetmanager.backend.api.command.store.CommandStore
import ch.jvi.budgetmanager.backend.core.event.TransferEvent.CreateTransferEvent
import ch.jvi.budgetmanager.backend.core.event.TransferEvent.UpdateTransferEvent
import ch.jvi.budgetmanager.backend.domain.account.AccountCommand.AdjustAccountBalanceCommand
import ch.jvi.budgetmanager.backend.domain.budget.BudgetCommand.AdjustBudgetBalanceCommand
import ch.jvi.budgetmanager.backend.domain.transfer.TransferCommand.CreateTransferCommand
import ch.jvi.budgetmanager.backend.domain.transfer.TransferCommand.UpdateTransferCommand
import ch.jvi.budgetmanager.core.api.EventListener
import org.springframework.stereotype.Component

@Component
class TransferEventListener(private val commandBus: CommandBus, private val commandStore: CommandStore) {

    @EventListener
    fun handle(createTransferEvent: CreateTransferEvent) {
        val createTransferCommand = convertToCreateTransferCommand(createTransferEvent)
        val updateRecipientCommand = converToUpdateRecipientAccountCommand(createTransferEvent)
        val updateSenderAccount = converToUpdateSenderAccountCommand(createTransferEvent)
        val updateBudgetCommand = convertToAdjustBudgetBalanceCommand(createTransferEvent)
        commandBus.sendAll(
            listOf(
                createTransferCommand,
                updateRecipientCommand,
                updateSenderAccount,
                updateBudgetCommand
            )
        )
        commandStore.saveCreationCommand(createTransferCommand)
        commandStore.saveAll(listOf(updateRecipientCommand, updateSenderAccount, updateBudgetCommand))
    }

    private fun convertToCreateTransferCommand(createTransferMessage: CreateTransferEvent): CreateTransferCommand {
        return CreateTransferCommand(
            recipientId = createTransferMessage.recipientId,
            senderId = createTransferMessage.senderId,
            amount = createTransferMessage.amount,
            budgetId = createTransferMessage.budgetId
        )
    }

    private fun converToUpdateRecipientAccountCommand(event: CreateTransferEvent): AdjustAccountBalanceCommand {
        return AdjustAccountBalanceCommand(
            entityId = event.recipientId,
            balanceChange = event.amount
        )
    }

    private fun converToUpdateSenderAccountCommand(event: CreateTransferEvent): AdjustAccountBalanceCommand {
        return AdjustAccountBalanceCommand(
            entityId = event.senderId,
            balanceChange = event.amount.negate()
        )
    }

    private fun convertToAdjustBudgetBalanceCommand(event: CreateTransferEvent): AdjustBudgetBalanceCommand {
        return AdjustBudgetBalanceCommand(
            amount = event.amount,
            entityId = event.budgetId
        )
    }

    @EventListener
    fun handle(updateTransferMessage: UpdateTransferEvent) {
        val updateTransferCommand = convertToUpdateTransferCommand(updateTransferMessage)
        commandBus.send(updateTransferCommand)
        commandStore.save(updateTransferCommand)
    }

    private fun convertToUpdateTransferCommand(event: UpdateTransferEvent): UpdateTransferCommand {
        return UpdateTransferCommand(
            entityId = event.id,
            recipientId = event.recipientId,
            senderId = event.senderId,
            amount = event.amount
        )
    }

}