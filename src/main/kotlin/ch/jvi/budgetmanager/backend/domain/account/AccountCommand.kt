package ch.jvi.budgetmanager.backend.domain.account

import ch.jvi.budgetmanager.backend.api.command.Command
import ch.jvi.budgetmanager.backend.domain.IDProvider
import java.math.BigDecimal
import java.math.BigDecimal.ZERO

sealed class AccountCommand {
    data class CreateAccountCommand(
        val balance: BigDecimal = ZERO,
        val name: String = "Anonymous Account",
        val id: String = IDProvider.next()
        ) : Command
}