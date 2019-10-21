package ch.jvi.budgetmanager.backend.core.event

import ch.jvi.budgetmanager.backend.api.event.Event
import java.math.BigDecimal

sealed class BudgetEvent : Event {
    data class CreateBudgetEvent(
        val name: String,
        val target: BigDecimal,
        val balance: BigDecimal
    ) : BudgetEvent()

    data class AdjustBudgetBalanceEvent(
        val id: String,
        val amount: BigDecimal
    ) : BudgetEvent()
}