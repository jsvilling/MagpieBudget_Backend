package ch.jvi.magpie.commandservice.transfer

import ch.jvi.magpie.command.domain.transfer.Transfer
import ch.jvi.magpie.commandservice.EventBus
import ch.jvi.magpie.commandservice.ITransferCommandStore
import ch.jvi.magpie.domain.transfer.ITransferService
import ch.jvi.magpie.domain.transfer.TransferCommand
import ch.jvi.magpie.domain.transfer.TransferCommand.CreateTransferCommand
import ch.jvi.magpie.domain.transfer.TransferCommand.UpdateTransferCommand
import ch.jvi.magpie.domain.transfer.TransferEvent.CreateTransferEvent
import ch.jvi.magpie.domain.transfer.TransferEvent.UpdateTransferEvent
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class TransferService(
    private val eventBus: EventBus,
    private val transferCommandStore: ITransferCommandStore
) : ITransferService {

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

    override fun findAllForAccount(accountId: String): List<Transfer> {
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
    override fun createTransfer(senderId: String, name: String, recipientId: String, amount: BigDecimal) {
        val creationCommand = CreateTransferCommand(recipientId, name, senderId, amount)
        transferCommandStore.save(creationCommand)

        val createTransferEvent = CreateTransferEvent(name, recipientId, senderId, amount)
        eventBus.send(createTransferEvent)
    }

    /**
     * Sends an UpdateTransferEvent with the given Data.
     */
    override fun updateTransfer(updateTransferEvent: UpdateTransferEvent) {
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