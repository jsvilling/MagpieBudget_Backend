package ch.jvi.budgetmanager.backend.core.event

import ch.jvi.budgetmanager.backend.api.event.Event
import ch.jvi.budgetmanager.backend.domain.transaction.TransactionType
import java.math.BigDecimal

sealed class TransactionEvent : Event {

    data class CreateTransactionEvent(
        val name: String,
        val amount: BigDecimal,
        val accountId: String,
        val type: TransactionType
    ) : TransactionEvent()

}