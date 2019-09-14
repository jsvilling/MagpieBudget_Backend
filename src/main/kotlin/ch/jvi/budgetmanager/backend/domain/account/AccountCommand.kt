package ch.jvi.budgetmanager.backend.domain.account

import ch.jvi.budgetmanager.backend.domain.Command
import java.math.BigDecimal

sealed class AccountCommand : Command {
    data class CreateAccountCommand(val balance: BigDecimal, val name: String)
}