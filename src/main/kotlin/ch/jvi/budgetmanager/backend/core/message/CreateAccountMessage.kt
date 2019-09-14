package ch.jvi.budgetmanager.backend.core.message

import ch.jvi.budgetmanager.core.api.Message
import java.math.BigDecimal

data class CreateAccountMessage(val balance: BigDecimal, val name: String) : Message