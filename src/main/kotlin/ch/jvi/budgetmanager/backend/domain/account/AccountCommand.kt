package ch.jvi.budgetmanager.backend.domain.account

import ch.jvi.budgetmanager.backend.api.command.Command
import ch.jvi.budgetmanager.backend.api.command.CreationCommand
import ch.jvi.budgetmanager.backend.domain.IDProvider
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal

/**
 * This sealed class contains all Commands that may be applied to an Account Entity.
 *
 * @author J. Villing
 */
@Document(collection = "command")
sealed class AccountCommand : Command {

    /**
     * Creation Command for a Account
     */
    data class CreateAccountCommand(
        val balance: BigDecimal = BigDecimal.ZERO,
        val name: String = "Anonymous Account",
        override val entityId: String = IDProvider.next(),
        @Id override val commandId: String = IDProvider.next()
    ) : AccountCommand(), CreationCommand

    /**
     * Re-Initialize name and balance of an Account.
     */
    data class UpdateAccountCommand(
        val balance: BigDecimal = BigDecimal.ZERO,
        val name: String = "",
        override val entityId: String,
        @Id override val commandId: String = IDProvider.next()
    ) : AccountCommand()

    /**
     * Adjust the balance of an Account by the given amount.
     */
    data class AdjustAccountBalanceCommand(
        val balanceChange: BigDecimal,
        override val entityId: String,
        @Id override val commandId: String = IDProvider.next()
    ) : AccountCommand()
}