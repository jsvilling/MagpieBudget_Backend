package ch.jvi.budgetmanager.backend.domain.transaction

import ch.jvi.budgetmanager.backend.api.command.Command
import ch.jvi.budgetmanager.backend.api.command.CreationCommand
import ch.jvi.budgetmanager.backend.domain.IDProvider
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.math.BigDecimal

@Document(collection = "command")
sealed class TransactionCommand : Command {

    data class CreateTransactionCommand(
        val name: String,
        val amount: BigDecimal,
        val accountId: String,
        val type: TransactionType,
        override val entityId: String = IDProvider.next(),
        @Id override val commandId: String = IDProvider.next()
    ) : TransactionCommand(), CreationCommand

}