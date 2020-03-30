package ch.jvi.budgetmanager.backend.domain.transfer

import ch.jvi.budgetmanager.backend.api.command.Command
import ch.jvi.budgetmanager.backend.api.command.CreationCommand
import ch.jvi.budgetmanager.backend.domain.IDProvider
import org.springframework.data.annotation.Id
import java.math.BigDecimal

/**
 * This sealed class contains all commands for creating and updating a Transfer.
 *
 * @author J. Villing
 */
sealed class TransferCommand : Command {

    /**
     * Is used to create a Transfer
     */
    data class CreateTransferCommand(
        val recipientId: String,
        val name: String,
        val senderId: String,
        val amount: BigDecimal,
        val budgetId: String,
        override val entityId: String = IDProvider.next(),
        @Id override val commandId: String = IDProvider.next()
    ) : TransferCommand(), CreationCommand

    /**
     * Is used to reset sender, recipient and amount of a transfer.
     */
    data class UpdateTransferCommand(
        val name: String,
        val recipientId: String,
        val senderId: String,
        val amount: BigDecimal,
        override val entityId: String = IDProvider.next(),
        @Id override val commandId: String = IDProvider.next()
    ) : TransferCommand()
}