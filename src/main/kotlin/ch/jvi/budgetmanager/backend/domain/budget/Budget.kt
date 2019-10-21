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

    val name = creationCommand.name

    var target = creationCommand.target
        private set

    var balance = creationCommand.balance
        private set

    override fun apply(command: BudgetCommand) = when (command) {
        is CreateBudgetCommand -> apply(command)
        is BudgetCommand.AdjustBudgetBalanceCommand -> apply(command)
    }

    private fun apply(command: CreateBudgetCommand): Nothing =
        throw IllegalArgumentException("Creation commands cannot be applied to existing account")

    private fun apply(command: BudgetCommand.AdjustBudgetBalanceCommand) {
        balance = balance.add(command.amount)
    }
}