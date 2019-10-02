package ch.jvi.budgetmanager.backend.domain.account

import ch.jvi.budgetmanager.backend.domain.DomainEntity
import ch.jvi.budgetmanager.backend.domain.account.AccountCommand.*
import java.math.BigDecimal

class Account(creationCommand: CreateAccountCommand) : DomainEntity<AccountCommand> {

    val id: String = creationCommand.entityId

    var balance: BigDecimal = creationCommand.balance
        private set

    var name: String = creationCommand.name
        private set

    override fun apply(command: AccountCommand) = when (command) {
        is CreateAccountCommand -> apply(command)
        is UpdateAccountCommand -> apply(command)
        is AdjustAccountBalanceCommand -> apply(command)
    }

    private fun apply(command: CreateAccountCommand): Nothing =
        throw IllegalArgumentException("Creation commands cannot be applied to existing account")

    private fun apply(command: UpdateAccountCommand) {
        this.balance = command.balance
        this.name = command.name
    }

    private fun apply(command: AdjustAccountBalanceCommand) {
        this.balance += command.balanceChange
    }
}