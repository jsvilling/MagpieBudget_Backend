package ch.jvi.budgetmanager.backend.domain.budget

import ch.jvi.budgetmanager.backend.domain.DomainEntity
import ch.jvi.budgetmanager.backend.domain.budget.BudgetCommand.CreateBudgetCommand

/**
 * Domain class representing a budget.
 *
 * @author J. Villing
 */
class Budget(creationCommand: CreateBudgetCommand) : DomainEntity<BudgetCommand> {

    val id = creationCommand.entityId

    val target = creationCommand.target

    var balance = creationCommand.balance

    override fun apply(command: BudgetCommand) = when (command) {
        is CreateBudgetCommand -> apply(command)
    }

    private fun apply(command: CreateBudgetCommand): Nothing =
        throw IllegalArgumentException("Creation commands cannot be applied to existing account")
}