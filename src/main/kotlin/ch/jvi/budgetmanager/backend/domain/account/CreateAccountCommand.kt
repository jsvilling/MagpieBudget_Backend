package ch.jvi.budgetmanager.backend.domain.account

import ch.jvi.budgetmanager.backend.api.command.CreationCommand
import ch.jvi.budgetmanager.backend.domain.IDProvider
import java.math.BigDecimal
import java.math.BigDecimal.ZERO

data class CreateAccountCommand(
    val balance: BigDecimal = ZERO,
    val name: String = "Anonymous Account",
    override val id: String = IDProvider.next()
) : CreationCommand