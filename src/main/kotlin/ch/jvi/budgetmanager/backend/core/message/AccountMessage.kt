package ch.jvi.budgetmanager.backend.core.message

import ch.jvi.budgetmanager.backend.api.message.Message
import java.math.BigDecimal

sealed class AccountMessage : Message {
    data class CreateAccountMessage(val balance: BigDecimal, val name: String) : AccountMessage()
    data class UpdateAccountMessage(val id: String, val balance: BigDecimal, val name: String) : AccountMessage()
}