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

    /**
     * @return The transfer with the requested ID
     * @throws IllegalArgumentException if no Entity with the given ID is found.
     */
    override fun find(entity: String): Transfer {
        val createTransferCommand = commandStore.findCreationCommand(entity) as CreateTransferCommand
        val transferCommands = commandStore.findTransferCommands(entity)
        return Transfer(createTransferCommand)
    }

    /**
     * Creates and sends a CreateTransferEvent with the given data.
     */
    fun createTransfer(senderId: String, recipientId: String, amount: BigDecimal, budgetId: String) {
        val createTransferMessage =
            CreateTransferEvent(recipientId = recipientId, senderId = senderId, amount = amount, budgetId = budgetId)
        eventBus.send(createTransferMessage)
    }

    /**
     * Creates and sends a UpdateTransferEvent with the given Data.
     */
    fun updateTransfer(id: String, senderId: String, recipientId: String, amount: BigDecimal) {
        val updateTransferMessage = UpdateTransferEvent(id, recipientId, senderId, amount)
        eventBus.send(updateTransferMessage)
    }

}