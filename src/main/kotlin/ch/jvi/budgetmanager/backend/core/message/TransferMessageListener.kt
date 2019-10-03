package ch.jvi.budgetmanager.backend.core.message

import ch.jvi.budgetmanager.backend.api.command.bus.CommandBus
import ch.jvi.budgetmanager.backend.api.command.store.CommandStore
import ch.jvi.budgetmanager.backend.domain.account.AccountCommand.AdjustAccountBalanceCommand
import ch.jvi.budgetmanager.backend.domain.transfer.TransferCommand.CreateTransferCommand
import ch.jvi.budgetmanager.backend.domain.transfer.TransferCommand.UpdateTransferCommand
import ch.jvi.budgetmanager.core.api.MessageListener
import org.springframework.stereotype.Component

@Component
class TransferMessageListener(private val commandBus: CommandBus, private val commandStore: CommandStore) {

    @MessageListener
    fun handle(createTransferMessage: TransferMessage.CreateTransferMessage) {
        val createTransferCommand = convertToCreateTransferCommand(createTransferMessage)
        val updateRecipientCommand = converToUpdateRecipientAccountCommand(createTransferMessage)
        val updateSenderAccount = converToUpdateSenderAccountCommand(createTransferMessage)
        commandBus.sendAll(listOf(createTransferCommand, updateRecipientCommand, updateSenderAccount))
        commandStore.saveCreationCommand(createTransferCommand)
        commandStore.saveAll(listOf(updateRecipientCommand, updateSenderAccount))
    }

    private fun convertToCreateTransferCommand(createTransferMessage: TransferMessage.CreateTransferMessage): CreateTransferCommand {
        return CreateTransferCommand(
            recipientId = createTransferMessage.recipientId,
            senderId = createTransferMessage.senderId,
            amount = createTransferMessage.amount
        )
    }

    private fun converToUpdateRecipientAccountCommand(createTransferMessage: TransferMessage.CreateTransferMessage): AdjustAccountBalanceCommand {
        return AdjustAccountBalanceCommand(
            entityId = createTransferMessage.recipientId,
            balanceChange = createTransferMessage.amount
        )
    }

    private fun converToUpdateSenderAccountCommand(createTransferMessage: TransferMessage.CreateTransferMessage): AdjustAccountBalanceCommand {
        return AdjustAccountBalanceCommand(
            entityId = createTransferMessage.senderId,
            balanceChange = createTransferMessage.amount.negate()
        )
    }

    @MessageListener
    fun handle(updateTransferMessage: TransferMessage.UpdateTransferMessage) {
        val updateTransferCommand = convertToUpdateTransferCommand(updateTransferMessage)
        commandBus.send(updateTransferCommand)
        commandStore.save(updateTransferCommand)
    }

    private fun convertToUpdateTransferCommand(updateTransferMessage: TransferMessage.UpdateTransferMessage): UpdateTransferCommand {
        return UpdateTransferCommand(
            entityId = updateTransferMessage.id,
            recipientId = updateTransferMessage.recipientId,
            senderId = updateTransferMessage.senderId,
            amount = updateTransferMessage.amount
        )
    }

}