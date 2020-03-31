package ch.jvi.budgetmanager.backend.core.service

import ch.jvi.budgetmanager.backend.api.command.CreationCommand
import ch.jvi.budgetmanager.backend.api.command.store.CommandStore
import ch.jvi.budgetmanager.backend.api.event.EventBus
import ch.jvi.budgetmanager.backend.api.service.EntityService
import ch.jvi.budgetmanager.backend.core.event.TransferEvent.CreateTransferEvent
import ch.jvi.budgetmanager.backend.core.event.TransferEvent.UpdateTransferEvent
import ch.jvi.budgetmanager.backend.domain.transfer.Transfer
import ch.jvi.budgetmanager.backend.domain.transfer.TransferCommand
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
    override fun find(entityId: String): Transfer {
        val createTransferCommand = commandStore.findCreationCommand(entityId) as CreateTransferCommand
        val transfer = Transfer(createTransferCommand)
        return applyCommands(transfer)
    }

    override fun findAll(): List<Transfer> {
        return commandStore.findCreationCommands(this::isTransferCreationCommand)
            .map { Transfer(it as CreateTransferCommand) }
            .map { applyCommands(it) }
    }

    fun findAllForAccount(accountId: String): List<Transfer> {
        return commandStore.findCreationCommands(this::isTransferCreationCommand)
            .map { Transfer(it as CreateTransferCommand) }
            .map { applyCommands(it) }
            .filter { it.recipientId == accountId || it.senderId == accountId }
    }

    private fun isTransferCreationCommand(command: CreationCommand): Boolean {
        return command is CreateTransferCommand;
    }

    private fun applyCommands(transfer: Transfer): Transfer {
        val commands: List<TransferCommand> = commandStore.findTransferCommands(transfer.id)
        transfer.applyAll(commands)
        return transfer
    }

    /**
     * Creates and sends a CreateTransferEvent with the given data.
     */
    fun createTransfer(senderId: String, name: String, recipientId: String, amount: BigDecimal, budgetId: String) {
        val createTransferEvent =
            CreateTransferEvent(
                recipientId = recipientId,
                name = name,
                senderId = senderId,
                amount = amount,
                budgetId = budgetId
            )
        eventBus.send(createTransferEvent)
    }

    /**
     * Creates and sends a UpdateTransferEvent with the given Data.
     */
    fun updateTransfer(updateTransferEvent: UpdateTransferEvent) {
        eventBus.send(updateTransferEvent)
    }

}