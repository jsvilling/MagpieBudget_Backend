package ch.jvi.budgetmanager.backend.domain.transfer

import ch.jvi.budgetmanager.backend.domain.DomainEntity
import ch.jvi.budgetmanager.backend.domain.transfer.TransferCommand.CreateTransferCommand
import ch.jvi.budgetmanager.backend.domain.transfer.TransferCommand.UpdateTransferCommand


/**
 * Domain Entity representing the transfer of an amount from one account to another
 *
 * @author J. Villing
 */
class Transfer(creationCommand: CreateTransferCommand) : DomainEntity<TransferCommand> {

    val id = creationCommand.entityId

    var recipientId = creationCommand.recipientId
        private set

    var senderId = creationCommand.senderId
        private set

    var amount = creationCommand.amount
        private set

    var budgetId = creationCommand.budgetId

    override fun apply(command: TransferCommand) = when (command) {
        is CreateTransferCommand -> apply(command)
        is UpdateTransferCommand -> apply(command)
    }

    private fun apply(command: CreateTransferCommand): Nothing =
        throw IllegalArgumentException("Creation commands cannot be applied to existing account")

    private fun apply(command: UpdateTransferCommand) {
        recipientId = command.recipientId
        senderId = command.senderId
        amount = command.amount
    }
}