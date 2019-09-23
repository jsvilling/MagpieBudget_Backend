package ch.jvi.budgetmanager.backend.core

import ch.jvi.budgetmanager.backend.api.command.CommandStore
import ch.jvi.budgetmanager.backend.api.message.MessageBus
import ch.jvi.budgetmanager.backend.core.message.TransferMessage
import ch.jvi.budgetmanager.backend.core.message.TransferMessage.UpdateTransferMessage
import ch.jvi.budgetmanager.backend.domain.transfer.Transfer
import ch.jvi.budgetmanager.backend.domain.transfer.TransferCommand.CreateTransferCommand
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class TransferService(private val commandStore: CommandStore, private val messageBus: MessageBus) {

    fun getTransfer(id: String): Transfer {
        val createTransferCommand = commandStore.findCreationCommand(id) as CreateTransferCommand
        val transferCommands = commandStore.findTransferCommands(id)
        return Transfer(createTransferCommand)
    }

    fun createTransfer(senderId: String, recipientId: String, amount: BigDecimal) {
        val createTransferMessage = TransferMessage.CreateTransferMessage(recipientId, senderId, amount)
        messageBus.send(createTransferMessage)
    }

    fun updateTransfer(id: String, senderId: String, recipientId: String, amount: BigDecimal) {
        val updateTransferMessage = UpdateTransferMessage(id, recipientId, senderId, amount)
        messageBus.send(updateTransferMessage)
    }

}