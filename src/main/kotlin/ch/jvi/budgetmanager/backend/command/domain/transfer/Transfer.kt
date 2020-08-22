package ch.jvi.budgetmanager.backend.command.domain.transfer

import ch.jvi.budgetmanager.backend.command.domain.DomainEntity
import ch.jvi.budgetmanager.backend.command.domain.transfer.command.TransferCommand
import ch.jvi.budgetmanager.backend.command.domain.transfer.command.TransferCommand.CreateTransferCommand
import ch.jvi.budgetmanager.backend.command.domain.transfer.command.TransferCommand.UpdateTransferCommand


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

    private fun apply(command: UpdateTransferCommand) {
        name = command.name
        recipientId = command.recipientId
        senderId = command.senderId
        amount = command.amount
    }
}