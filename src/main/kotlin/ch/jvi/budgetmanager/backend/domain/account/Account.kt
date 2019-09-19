package ch.jvi.budgetmanager.backend.domain.account

import ch.jvi.budgetmanager.backend.domain.DomainEntity
import java.math.BigDecimal

class Account(creationCommand: CreateAccountCommand) : DomainEntity {

    val id: String = creationCommand.id;

    var balance: BigDecimal = creationCommand.balance
        private set

    var name: String = creationCommand.name
        private set

    fun apply(command: UpdateAccountCommand) {
        this.balance = command.balance
        this.name = command.name
    }

}