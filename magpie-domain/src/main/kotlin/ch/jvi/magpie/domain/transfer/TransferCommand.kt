package ch.jvi.magpie.domain.transfer

import ch.jvi.magpie.domain.Command
import ch.jvi.magpie.domain.CreationCommand
import ch.jvi.magpie.domain.IDProvider
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
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
    @Document(collection = "TransferCreationCommand")
    data class CreateTransferCommand(
        val recipientId: String,
        val name: String,
        val senderId: String,
        val amount: BigDecimal,
        override val entityId: String = IDProvider.next(),
        @Id override val commandId: String = IDProvider.next()
    ) : TransferCommand(), CreationCommand

    /**
     * Is used to reset sender, recipient and amount of a transfer.
     */
    @Document(collection = "TransferUpdateCommand")
    data class UpdateTransferCommand(
        val name: String,
        val recipientId: String,
        val senderId: String,
        val amount: BigDecimal,
        override val entityId: String = IDProvider.next(),
        @Id override val commandId: String = IDProvider.next()
    ) : TransferCommand()
}