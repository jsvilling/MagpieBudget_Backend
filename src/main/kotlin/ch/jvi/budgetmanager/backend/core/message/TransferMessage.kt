package ch.jvi.budgetmanager.backend.core.message

import ch.jvi.budgetmanager.backend.api.message.Message
import java.math.BigDecimal

sealed class TransferMessage : Message {
    data class CreateTransferMessage(
        val recipientId: String,
        val senderId: String,
        val amount: BigDecimal
    ) : TransferMessage()
    data class UpdateTransferMessage(
        val id: String,
        val recipientId: String,
        val senderId: String,
        val amount: BigDecimal
    ): TransferMessage()
}