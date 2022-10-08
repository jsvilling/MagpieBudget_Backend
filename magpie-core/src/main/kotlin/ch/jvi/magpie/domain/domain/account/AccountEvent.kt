package ch.jvi.magpie.domain.domain.account

import ch.jvi.magpie.domain.api.Event
import java.math.BigDecimal

sealed class AccountEvent : Event {
    data class CreateAccountEvent(val balance: BigDecimal, val name: String) : AccountEvent()
    data class UpdateAccountEvent(val id: String, val balance: BigDecimal, val name: String) : AccountEvent()
}
