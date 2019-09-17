package ch.jvi.budgetmanager.backend.core.message

import ch.jvi.budgetmanager.backend.api.message.Message
import java.math.BigDecimal

data class UpdateAccountMessage(val id: String, val balance: BigDecimal, val name: String) : Message