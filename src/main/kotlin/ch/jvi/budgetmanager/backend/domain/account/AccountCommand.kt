package ch.jvi.budgetmanager.backend.domain.account

import ch.jvi.budgetmanager.backend.api.command.Command
import ch.jvi.budgetmanager.backend.api.command.CreationCommand
import ch.jvi.budgetmanager.backend.domain.IDProvider
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal

@Document(collection = "command")
sealed class AccountCommand : Command {

    data class CreateAccountCommand(
        val balance: BigDecimal = BigDecimal.ZERO,
        val name: String = "Anonymous Account",
        override val entityId: String = IDProvider.next(),
        @Id override val commandId: String = IDProvider.next()
    ) : AccountCommand(), CreationCommand

    data class UpdateAccountCommand(
        val balance: BigDecimal = BigDecimal.ZERO,
        val name: String = "",
        override val entityId: String,
        @Id override val commandId: String = IDProvider.next()
    ) : AccountCommand()

    data class AdjustAccountBalanceCommand(
        val balanceChange: BigDecimal,
        override val entityId: String,
        override val commandId: String = IDProvider.next()
    ) : AccountCommand()
}