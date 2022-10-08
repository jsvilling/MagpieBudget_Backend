package ch.jvi.magpie.core.domain.account

import ch.jvi.magpie.core.api.DomainEntity
import ch.jvi.magpie.core.domain.account.AccountCommand.*
import java.math.BigDecimal

/**
 * Domain Entity representing an account.
 *
 * A account represents any digital or physical entity that has a monetary value assigned. Examples may be a
 * bank account, a persons wallet or stock portfolio.
 *
 * @author J. Villing
 *
 */
class Account(private val creationCommand: CreateAccountCommand) : DomainEntity<AccountCommand> {

    val id: String = creationCommand.entityId

    var balance: BigDecimal = creationCommand.balance
        private set

    var name: String = creationCommand.name
        private set

    /**
     * @see DomainEntity.apply
     */
    override fun apply(command: AccountCommand) = when (command) {
        is CreateAccountCommand -> apply(command)
        is UpdateAccountCommand -> apply(command)
        is AdjustAccountBalanceCommand -> apply(command)
    } // TODO: Validate applied commands are valid and lead to valid state

    private fun apply(command: CreateAccountCommand): Nothing =
        throw IllegalArgumentException("Creation commands cannot be applied to existing account")

    private fun apply(command: UpdateAccountCommand) {
        this.balance = command.balance
        this.name = command.name
    }

    private fun apply(command: AdjustAccountBalanceCommand) {
        this.balance = balance.add(command.balanceChange);
    }
}
