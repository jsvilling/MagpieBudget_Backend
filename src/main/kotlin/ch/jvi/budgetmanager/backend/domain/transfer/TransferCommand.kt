package ch.jvi.budgetmanager.backend.domain.transfer

import ch.jvi.budgetmanager.backend.api.command.Command
import ch.jvi.budgetmanager.backend.api.command.CreationCommand
import ch.jvi.budgetmanager.backend.domain.IDProvider
import java.math.BigDecimal

sealed class TransferCommand : Command {
    data class CreateTransferCommand(
        val recipientId: String,
        val senderId: String,
        val amount: BigDecimal,
        override val id: String = IDProvider.next()
    ) : TransferCommand(), CreationCommand

    data class UpdateTransferCommand(
        val recipientId: String,
        val senderId: String,
        val amount: BigDecimal,
        override val id: String = IDProvider.next()
    ) : TransferCommand()
}