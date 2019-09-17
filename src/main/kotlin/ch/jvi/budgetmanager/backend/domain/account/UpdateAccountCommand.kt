package ch.jvi.budgetmanager.backend.domain.account

import ch.jvi.budgetmanager.backend.api.command.Command
import java.math.BigDecimal

data class UpdateAccountCommand(
    val balance: BigDecimal = BigDecimal.ZERO,
    val name: String = "Anonymous Account",
    override val id: String
) : Command