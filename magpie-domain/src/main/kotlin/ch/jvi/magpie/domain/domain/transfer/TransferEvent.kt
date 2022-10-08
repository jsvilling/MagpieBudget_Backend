package ch.jvi.magpie.domain.domain.transfer

import ch.jvi.magpie.domain.api.Event
import java.math.BigDecimal

sealed class TransferEvent : Event {
    data class CreateTransferEvent(
        val name: String,
        val recipientId: String,
        val senderId: String,
        val amount: BigDecimal
    ) : TransferEvent()

    data class UpdateTransferEvent(
        val transferId: String,
        val oldSenderId: String,
        val oldRecipientId: String,
        val oldAmount: BigDecimal,
        val newSenderId: String,
        val newRecipientId: String,
        val newAmount: BigDecimal,
        val newName: String
    ) : TransferEvent()
}
