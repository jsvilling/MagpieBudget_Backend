package ch.jvi.budgetmanager.backend.domain.account

import ch.jvi.budgetmanager.backend.domain.api.Command
import java.math.BigDecimal
import java.math.BigDecimal.ZERO

sealed class AccountCommand : Command {
    data class CreateAccountCommand(val balance: BigDecimal = ZERO, val name: String = "Anonymous Account")
}