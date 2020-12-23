package ch.jvi.magpie.domain.account.command

import ch.jvi.magpie.domain.Command
import ch.jvi.magpie.domain.IDProvider
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal

/**
 * This sealed class contains all Commands that may be applied to an Account Entity.
 *
 * @author J. Villing
 */
sealed class AccountCommand : Command {

    /**
     * Creation Command for a Account
     */
    @Document(collection = "AccountCreationCommand")
    data class CreateAccountCommand(
        val balance: BigDecimal = BigDecimal.ZERO,
        val name: String = "Anonymous Account",
        override val entityId: String = IDProvider.next(),
        @Id override val commandId: String = IDProvider.next()
    ) : AccountCommand()

    /**
     * Re-Initialize name and balance of an Account.
     */
    @Document(collection = "UpdateAccountCommand")
    data class UpdateAccountCommand(
        val balance: BigDecimal = BigDecimal.ZERO,
        val name: String = "",
        override val entityId: String,
        @Id override val commandId: String = IDProvider.next()
    ) : AccountCommand()

    /**
     * Adjust the balance of an Account by the given amount.
     */
    @Document(collection = "AdjustAccountBalanceCommand")
    data class AdjustAccountBalanceCommand(
        val balanceChange: BigDecimal,
        override val entityId: String,
        @Id override val commandId: String = IDProvider.next()
    ) : AccountCommand()
}