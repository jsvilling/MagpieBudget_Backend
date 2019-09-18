package ch.jvi.budgetmanager.backend.domain.account

import ch.jvi.budgetmanager.backend.api.command.Command
import ch.jvi.budgetmanager.backend.domain.DomainEntity
import java.math.BigDecimal

class Account(creationCommand: CreateAccountCommand) : DomainEntity {

    val id: String = creationCommand.id;
    val balance: BigDecimal = creationCommand.balance;
    val name: String = creationCommand.name

    fun apply(commands: List<Command>) {

    }

}