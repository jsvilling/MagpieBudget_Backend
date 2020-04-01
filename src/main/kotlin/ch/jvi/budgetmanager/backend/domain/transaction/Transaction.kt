package ch.jvi.budgetmanager.backend.domain.transaction

import ch.jvi.budgetmanager.backend.domain.DomainEntity
import ch.jvi.budgetmanager.backend.domain.transaction.TransactionCommand.CreateTransactionCommand
import ch.jvi.budgetmanager.backend.domain.transaction.TransactionCommand.UpdateTransactionCommand

/**
 * Domain Entity representing the transaction of an amount to or from an account.
 *
 * A transaction can be either income or an expense.
 *
 * @author J. Villing
 */
class Transaction(creationCommand: CreateTransactionCommand) : DomainEntity<TransactionCommand> {

    val id = creationCommand.entityId

    var name = creationCommand.name
        private set

    var amount = creationCommand.amount
        private set

    var accountId = creationCommand.accountId
        private set

    var type = creationCommand.type
        private set

    override fun apply(command: TransactionCommand) = when (command) {
        is CreateTransactionCommand -> apply(command)
        is UpdateTransactionCommand -> apply(command)
    }

    private fun apply(command: CreateTransactionCommand): Nothing =
        throw IllegalArgumentException("Creation commands cannot be applied to existing account")

    private fun apply(command: UpdateTransactionCommand) {
        this.name = command.name;
        this.amount = command.amount;
        this.accountId = command.accountId;
        this.type = command.type
    }

}