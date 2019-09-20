package ch.jvi.budgetmanager.backend.core.message

import ch.jvi.budgetmanager.backend.api.command.CommandBus
import ch.jvi.budgetmanager.backend.api.command.CommandStore
import ch.jvi.budgetmanager.backend.domain.account.AccountCommand
import ch.jvi.budgetmanager.backend.domain.transfer.TransferCommand
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

    private fun convertToCreateTransferCommand(createTransferMessage: TransferMessage.CreateTransferMessage): TransferCommand.CreateTransferCommand {
        return TransferCommand.CreateTransferCommand(
            recipientId = createTransferMessage.recipientId,
            senderId =  createTransferMessage.senderId,
            amount = createTransferMessage.amount
        )
    }

    private fun converToUpdateRecipientAccountCommand(createTransferMessage: TransferMessage.CreateTransferMessage): AccountCommand.UpdateAccountCommand {
        return AccountCommand.UpdateAccountCommand(
            id = createTransferMessage.recipientId,
            balance = createTransferMessage.amount
        )
    }

    private fun converToUpdateSenderAccountCommand(createTransferMessage: TransferMessage.CreateTransferMessage): AccountCommand.UpdateAccountCommand {
        return AccountCommand.UpdateAccountCommand(
            id = createTransferMessage.recipientId,
            balance = createTransferMessage.amount.negate()
        )
    }

}