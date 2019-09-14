package ch.jvi.budgetmanager.backend.domain.account

import ch.jvi.budgetmanager.backend.domain.DomainEntity
import java.math.BigDecimal

class Account : DomainEntity {

    val balance: BigDecimal;
    val name: String;

    constructor(creationCommand: AccountCommand.CreateAccountCommand) {
        this.balance = creationCommand.balance
        this.name = creationCommand.name
    }
}