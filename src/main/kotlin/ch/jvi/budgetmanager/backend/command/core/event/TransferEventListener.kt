package ch.jvi.budgetmanager.backend.command.core.event

import ch.jvi.budgetmanager.backend.command.api.command.bus.CommandBus
import ch.jvi.budgetmanager.backend.command.api.command.store.CommandStore
import ch.jvi.budgetmanager.backend.command.core.event.TransferEvent.CreateTransferEvent
import ch.jvi.budgetmanager.backend.command.core.event.TransferEvent.UpdateTransferEvent
import ch.jvi.budgetmanager.backend.command.domain.account.AccountCommand.AdjustAccountBalanceCommand
import ch.jvi.budgetmanager.backend.command.domain.transfer.TransferCommand.CreateTransferCommand
import ch.jvi.budgetmanager.backend.command.domain.transfer.TransferCommand.UpdateTransferCommand
import ch.jvi.budgetmanager.core.api.EventListener
import org.springframework.stereotype.Component

@Component
class TransferEventListener(private val commandBus: CommandBus, private val commandStore: CommandStore) {

    @EventListener
    fun handle(createTransferEvent: CreateTransferEvent) {
        val createTransferCommand = convertToCreateTransferCommand(createTransferEvent)
        val updateRecipientCommand = converToUpdateRecipientAccountCommand(createTransferEvent)
        val updateSenderAccount = converToUpdateSenderAccountCommand(createTransferEvent)
        commandBus.sendAll(
            listOf(
                createTransferCommand,
                updateRecipientCommand,
                updateSenderAccount
            )
        )
        commandStore.saveCreationCommand(createTransferCommand)
        commandStore.saveAll(listOf(updateRecipientCommand, updateSenderAccount))
    }

    private fun convertToCreateTransferCommand(event: CreateTransferEvent): CreateTransferCommand {
        return CreateTransferCommand(
            name = event.name,
            recipientId = event.recipientId,
            senderId = event.senderId,
            amount = event.amount
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

    @EventListener
    fun handle(updateTransferEvent: UpdateTransferEvent) {
        val updateTransferCommand = convertToUpdateTransferCommand(updateTransferEvent)

        // Update Old Recipient
        val updateOldRecipientCommand = AdjustAccountBalanceCommand(
            entityId = updateTransferEvent.oldRecipientId,
            balanceChange = updateTransferEvent.oldAmount.negate()
        )

        // Update Old Sender
        val updateOldSenderCommand = AdjustAccountBalanceCommand(
            entityId = updateTransferEvent.oldSenderId,
            balanceChange = updateTransferEvent.oldAmount
        )

        // Update new Recipient
        val updateNewRecipientCommand = AdjustAccountBalanceCommand(
            entityId = updateTransferEvent.newRecipientId,
            balanceChange = updateTransferEvent.newAmount
        )

        // Update new Sender
        val updateNewSenderCommand = AdjustAccountBalanceCommand(
            entityId = updateTransferEvent.newSenderId,
            balanceChange = updateTransferEvent.newAmount.negate()
        )

        val updateCommands = listOf(
            updateTransferCommand,
            updateOldRecipientCommand,
            updateOldSenderCommand,
            updateNewRecipientCommand,
            updateNewSenderCommand
        )

        commandBus.sendAll(updateCommands)
        commandStore.saveAll(updateCommands)
    }

    private fun convertToUpdateTransferCommand(event: UpdateTransferEvent): UpdateTransferCommand {
        return UpdateTransferCommand(
            entityId = event.transferId,
            recipientId = event.newRecipientId,
            senderId = event.newSenderId,
            amount = event.newAmount,
            name = event.newName
        )
    }

}