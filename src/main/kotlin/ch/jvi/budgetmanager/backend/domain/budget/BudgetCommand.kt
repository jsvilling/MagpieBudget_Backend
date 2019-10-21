package ch.jvi.budgetmanager.backend.domain.budget

import ch.jvi.budgetmanager.backend.api.command.Command
import ch.jvi.budgetmanager.backend.api.command.CreationCommand
import ch.jvi.budgetmanager.backend.domain.IDProvider
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal
import java.math.BigDecimal.ZERO

@Document(collection = "command")
sealed class BudgetCommand : Command {
    data class CreateBudgetCommand(
        val name: String,
        val target: BigDecimal,
        val balance: BigDecimal = ZERO,
        override val entityId: String = IDProvider.next(),
        @Id override val commandId: String = IDProvider.next()
    ) : BudgetCommand(), CreationCommand

    data class AdjustBudgetBalanceCommand(
        val amount: BigDecimal,
        override val entityId: String = IDProvider.next(),
        @Id override val commandId: String = IDProvider.next()
    ) : BudgetCommand()
}