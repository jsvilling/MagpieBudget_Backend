package ch.jvi.budgetmanager.backend.core.message

import ch.jvi.budgetmanager.backend.api.command.CommandBus
import ch.jvi.budgetmanager.backend.api.command.CommandStore
import ch.jvi.budgetmanager.backend.api.command.CreationCommand
import ch.jvi.budgetmanager.backend.domain.account.CreateAccountCommand
import ch.jvi.budgetmanager.core.api.MessageListener
import org.springframework.stereotype.Component

@Component
class AccountMessageListener(private val commandStore: CommandStore, private val commandBus: CommandBus) {

    @MessageListener
    fun handle(createAccountMessage: CreateAccountMessage) {
        val createAccountCommand = convertToCreationCommand(createAccountMessage)
        commandStore.saveCreationCommand(createAccountCommand)
        commandBus.send(createAccountCommand)
    }

    fun convertToCreationCommand(createAccountMessage: CreateAccountMessage): CreationCommand {
        return CreateAccountCommand(
            balance = createAccountMessage.balance,
            name = createAccountMessage.name
        )
    }
}