package ch.jvi.magpie.command.domain.transfer

import ch.jvi.magpie.core.api.DomainEntity
import ch.jvi.magpie.core.domain.transfer.TransferCommand
import ch.jvi.magpie.core.domain.transfer.TransferCommand.CreateTransferCommand
import ch.jvi.magpie.core.domain.transfer.TransferCommand.UpdateTransferCommand
import ch.jvi.magpie.core.domain.transfer.TransferEvent


/**
 * Domain Entity representing the transfer of an amount from one account to another
 *
 * @author J. Villing
 */
class Transfer(creationCommand: CreateTransferCommand) : DomainEntity<TransferCommand> {

    val id = creationCommand.entityId

    var name = creationCommand.name
        private set

    var recipientId = creationCommand.recipientId
        private set

    var senderId = creationCommand.senderId
        private set

    var amount = creationCommand.amount
        private set

    override fun apply(command: TransferCommand) = when (command) {
        is CreateTransferCommand -> apply(command)
        is UpdateTransferCommand -> apply(command)
    }

    private fun apply(command: CreateTransferCommand): Nothing =
        throw IllegalArgumentException("Creation commands cannot be applied to existing account")

    private fun apply(command: UpdateTransferCommand): TransferEvent {
        val updateEvent = TransferEvent.UpdateTransferEvent(
            transferId = command.entityId,
            newRecipientId = command.recipientId,
            oldRecipientId = recipientId,
            newSenderId = command.senderId,
            oldSenderId = senderId,
            newAmount = command.amount,
            oldAmount = amount,
            newName = command.name
        )

        name = command.name
        recipientId = command.recipientId
        senderId = command.senderId
        amount = command.amount

        return updateEvent
    }
}
