package ch.jvi.budgetmanager.backend.core.service

import ch.jvi.budgetmanager.backend.api.command.store.CommandStore
import ch.jvi.budgetmanager.backend.api.event.EventBus
import ch.jvi.budgetmanager.backend.api.service.EntityService
import ch.jvi.budgetmanager.backend.core.event.TransferEvent.CreateTransferEvent
import ch.jvi.budgetmanager.backend.core.event.TransferEvent.UpdateTransferEvent
import ch.jvi.budgetmanager.backend.domain.transfer.Transfer
import ch.jvi.budgetmanager.backend.domain.transfer.TransferCommand.CreateTransferCommand
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class TransferService(
    private val commandStore: CommandStore,
    private val eventBus: EventBus
) :
    EntityService<Transfer> {

    override fun find(entity: String): Transfer {
        val createTransferCommand = commandStore.findCreationCommand(entity) as CreateTransferCommand
        val transferCommands = commandStore.findTransferCommands(entity)
        return Transfer(createTransferCommand)
    }

    fun createTransfer(senderId: String, recipientId: String, amount: BigDecimal) {
        val createTransferMessage =
            CreateTransferEvent(recipientId = recipientId, senderId = senderId, amount = amount)
        eventBus.send(createTransferMessage)
    }

    fun updateTransfer(id: String, senderId: String, recipientId: String, amount: BigDecimal) {
        val updateTransferMessage = UpdateTransferEvent(id, recipientId, senderId, amount)
        eventBus.send(updateTransferMessage)
    }

}