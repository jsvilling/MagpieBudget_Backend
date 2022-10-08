package ch.jvi.magpie.service.transfer

import ch.jvi.magpie.command.domain.transfer.Transfer
import ch.jvi.magpie.service.EventBus
import ch.jvi.magpie.core.domain.transfer.ITransferCommandStore
import ch.jvi.magpie.core.domain.transfer.ITransferService
import ch.jvi.magpie.core.domain.transfer.TransferCommand
import ch.jvi.magpie.core.domain.transfer.TransferCommand.CreateTransferCommand
import ch.jvi.magpie.core.domain.transfer.TransferCommand.UpdateTransferCommand
import ch.jvi.magpie.core.domain.transfer.TransferEvent.CreateTransferEvent
import ch.jvi.magpie.core.domain.transfer.TransferEvent.UpdateTransferEvent
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
@Transactional(readOnly = true)
class TransferService(
    private val eventBus: EventBus,
    private val transferCommandStore: ITransferCommandStore
) : ITransferService {

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
    @Transactional
    override fun create(senderId: String, name: String, recipientId: String, amount: BigDecimal) {
        val creationCommand = CreateTransferCommand(recipientId, name, senderId, amount)
        transferCommandStore.save(creationCommand)

        val createTransferEvent = CreateTransferEvent(name, recipientId, senderId, amount)
        eventBus.send(createTransferEvent)
    }

    @Transactional
    override fun update(command: UpdateTransferCommand) {
        val transfer = find(command.entityId)

        val updateEvent = UpdateTransferEvent(
            transferId = command.entityId,
            newRecipientId = command.recipientId,
            oldRecipientId = transfer.recipientId,
            newSenderId = command.senderId,
            oldSenderId = transfer.senderId,
            newAmount = command.amount,
            oldAmount = transfer.amount,
            newName = command.name
        )

        transfer.apply(command)
        transferCommandStore.save(command)
        eventBus.send(updateEvent)
    }

}
