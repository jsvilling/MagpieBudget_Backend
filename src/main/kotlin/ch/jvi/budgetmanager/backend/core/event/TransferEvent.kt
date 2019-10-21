package ch.jvi.budgetmanager.backend.core.event

import ch.jvi.budgetmanager.backend.api.event.Event
import java.math.BigDecimal

sealed class TransferEvent : Event {
    data class CreateTransferEvent(
        val recipientId: String,
        val senderId: String,
        val amount: BigDecimal,
        val budgetId: String
    ) : TransferEvent()

    data class UpdateTransferEvent(
        val id: String,
        val recipientId: String,
        val senderId: String,
        val amount: BigDecimal
    ) : TransferEvent()
}