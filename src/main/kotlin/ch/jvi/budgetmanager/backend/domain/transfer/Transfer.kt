package ch.jvi.budgetmanager.backend.domain.transfer

import ch.jvi.budgetmanager.backend.domain.DomainEntity
import ch.jvi.budgetmanager.backend.domain.transfer.TransferCommand.*

class Transfer(creationCommand: CreateTransferCommand) : DomainEntity<TransferCommand> {

    val id = creationCommand.id
    val recipientId = creationCommand.recipientId
    val senderId = creationCommand.senderId
    val amount = creationCommand.amount

    override fun apply(command: TransferCommand) = when(command) {
        is CreateTransferCommand -> apply(command)
    }

    private fun apply(command: CreateTransferCommand): Nothing =
        throw IllegalArgumentException("Creation commands cannot be applied to existing account")
}