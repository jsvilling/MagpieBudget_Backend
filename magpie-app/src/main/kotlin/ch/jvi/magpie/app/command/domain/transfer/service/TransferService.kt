package ch.jvi.magpie.command.domain.transfer.service

import ch.jvi.magpie.command.domain.api.EntityService
import ch.jvi.magpie.command.domain.transfer.Transfer
import ch.jvi.magpie.command.domain.transfer.command.TransferCommand
import ch.jvi.magpie.command.domain.transfer.command.TransferCommand.CreateTransferCommand
import ch.jvi.magpie.command.domain.transfer.command.TransferCommand.UpdateTransferCommand
import ch.jvi.magpie.command.domain.transfer.event.TransferEvent.CreateTransferEvent
import ch.jvi.magpie.command.domain.transfer.event.TransferEvent.UpdateTransferEvent
import ch.jvi.magpie.command.domain.transfer.persistence.store.TransferCommandStore
import ch.jvi.magpie.event.api.EventBus
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class TransferService(
    private val eventBus: EventBus,
    private val transferCommandStore: TransferCommandStore
) :
    EntityService<Transfer> {

    /**
     * @return The transfer with the requested ID
     * @throws IllegalArgumentException if no Entity with the given ID is found.
     */
    override fun find(entityId: String): Transfer {
        val createTransferCommand: CreateTransferCommand = transferCommandStore.findCreationCommand(entityId)
        val transfer = Transfer(createTransferCommand)
        return applyCommands(transfer)
    }

    override fun findAll(): List<Transfer> {
        return transferCommandStore.findAllCreationCommands()
            .map { Transfer(it) }
            .map { applyCommands(it) }
    }

    fun findAllForAccount(accountId: String): List<Transfer> {
        // TODO: Implement a more efficient way to do this
        return findAll().filter { it.recipientId == accountId || it.senderId == accountId }
    }

    private fun applyCommands(transfer: Transfer): Transfer {
        val commands: List<TransferCommand> = transferCommandStore.findUpdateCommands(transfer.id)
        transfer.applyAll(commands)
        return transfer
    }

    /**
     * Creates and sends a CreateTransferEvent with the given data.
     */
    fun createTransfer(senderId: String, name: String, recipientId: String, amount: BigDecimal) {
        val creationCommand = CreateTransferCommand(recipientId, name, senderId, amount)
        transferCommandStore.save(creationCommand)

        val createTransferEvent = CreateTransferEvent(name, recipientId, senderId, amount)
        eventBus.send(createTransferEvent)
    }

    /**
     * Sends an UpdateTransferEvent with the given Data.
     */
    fun updateTransfer(updateTransferEvent: UpdateTransferEvent) {
        val updateTransferCommand = UpdateTransferCommand(
            entityId = updateTransferEvent.transferId,
            recipientId = updateTransferEvent.newRecipientId,
            senderId = updateTransferEvent.newSenderId,
            amount = updateTransferEvent.newAmount,
            name = updateTransferEvent.newName
        )
        transferCommandStore.save(updateTransferCommand)
        eventBus.send(updateTransferEvent)
    }

}