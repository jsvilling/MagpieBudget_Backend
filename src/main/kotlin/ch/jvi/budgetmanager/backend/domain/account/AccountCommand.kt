package ch.jvi.budgetmanager.backend.domain.account

import ch.jvi.budgetmanager.backend.api.command.Command
import ch.jvi.budgetmanager.backend.api.command.CreationCommand
import ch.jvi.budgetmanager.backend.domain.IDProvider
import java.math.BigDecimal

sealed class AccountCommand(override val commandId: String = IDProvider.next()) : Command {

    data class CreateAccountCommand(
        val balance: BigDecimal = BigDecimal.ZERO,
        val name: String = "Anonymous Account",
        override val entityId: String = IDProvider.next()
    ) : AccountCommand(), CreationCommand

    data class UpdateAccountCommand(
        val balance: BigDecimal = BigDecimal.ZERO,
        val name: String = "",
        override val entityId: String
    ) : AccountCommand()

    data class AdjustAccountBalanceCommand(
        val balanceChange: BigDecimal,
        override val entityId: String,
        override val commandId: String = IDProvider.next()
    ) : AccountCommand()
}