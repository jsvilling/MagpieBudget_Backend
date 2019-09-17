package ch.jvi.budgetmanager.backend.core.message

import ch.jvi.budgetmanager.backend.api.command.CommandBus
import ch.jvi.budgetmanager.backend.api.command.CommandStore
import ch.jvi.budgetmanager.backend.api.command.CreationCommand
import ch.jvi.budgetmanager.backend.domain.account.CreateAccountCommand
import ch.jvi.budgetmanager.backend.domain.account.UpdateAccountCommand
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

    private fun convertToCreationCommand(createAccountMessage: CreateAccountMessage): CreationCommand {
        return CreateAccountCommand(
            balance = createAccountMessage.balance,
            name = createAccountMessage.name
        )
    }

    @MessageListener
    fun handle(updateAccountMessage: UpdateAccountMessage) {
        val updateAccountCommand = convertToUpdateCommand(updateAccountMessage)
        commandStore.save(updateAccountCommand)
        commandBus.send(updateAccountCommand)
    }

    fun convertToUpdateCommand(updateAccountMessage: UpdateAccountMessage): UpdateAccountCommand {
        return UpdateAccountCommand(
            id = updateAccountMessage.id,
            balance = updateAccountMessage.balance,
            name = updateAccountMessage.name
        )
    }
}