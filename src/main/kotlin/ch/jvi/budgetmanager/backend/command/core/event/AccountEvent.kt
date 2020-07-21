package ch.jvi.budgetmanager.backend.command.core.event

import ch.jvi.budgetmanager.backend.command.api.event.Event
import java.math.BigDecimal

sealed class AccountEvent : Event {
    data class CreateAccountEvent(val balance: BigDecimal, val name: String) : AccountEvent()
    data class UpdateAccountEvent(val id: String, val balance: BigDecimal, val name: String) : AccountEvent()
}