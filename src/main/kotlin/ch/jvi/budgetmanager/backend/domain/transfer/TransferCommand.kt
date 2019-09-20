package ch.jvi.budgetmanager.backend.domain.transfer

import ch.jvi.budgetmanager.backend.api.command.Command
import ch.jvi.budgetmanager.backend.api.command.CreationCommand
import ch.jvi.budgetmanager.backend.domain.IDProvider

sealed class TransferCommand : Command {
    data class CreateTransferCommand(
        override val id: String = IDProvider.next()
    ) : TransferCommand(), CreationCommand
}