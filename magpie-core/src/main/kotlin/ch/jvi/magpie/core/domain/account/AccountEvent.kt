package ch.jvi.magpie.core.domain.account

import ch.jvi.magpie.core.api.Event
import java.math.BigDecimal

sealed class AccountEvent : Event {
    data class CreateAccountEvent(val balance: BigDecimal, val name: String) : AccountEvent()
    data class UpdateAccountEvent(val id: String, val balance: BigDecimal, val name: String) : AccountEvent()
}
