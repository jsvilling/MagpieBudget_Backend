package ch.jvi.budgetmanager.backend.core.message

import ch.jvi.budgetmanager.backend.api.command.bus.CommandBus
import ch.jvi.budgetmanager.backend.api.command.store.CommandStore
import ch.jvi.budgetmanager.backend.api.command.CreationCommand
import ch.jvi.budgetmanager.backend.domain.account.AccountCommand
import ch.jvi.budgetmanager.backend.domain.account.AccountCommand.UpdateAccountCommand
import ch.jvi.budgetmanager.core.api.MessageListener
import org.springframework.stereotype.Component

@Component
class AccountMessageListener(
    private val commandStore: CommandStore,
    private val commandBus: CommandBus
) {

    @MessageListener
    fun handle(createAccountMessage: AccountMessage.CreateAccountMessage) {
        val createAccountCommand = convertToCreationCommand(createAccountMessage)
        commandStore.saveCreationCommand(createAccountCommand)
        commandBus.send(createAccountCommand)
    }

    private fun convertToCreationCommand(createAccountMessage: AccountMessage.CreateAccountMessage): CreationCommand {
        return AccountCommand.CreateAccountCommand(
            balance = createAccountMessage.balance,
            name = createAccountMessage.name
        )
    }

    @MessageListener
    fun handle(updateAccountMessage: AccountMessage.UpdateAccountMessage) {
        val updateAccountCommand = convertToUpdateCommand(updateAccountMessage)
        commandStore.save(updateAccountCommand)
        commandBus.send(updateAccountCommand)
    }

    fun convertToUpdateCommand(updateAccountMessage: AccountMessage.UpdateAccountMessage): UpdateAccountCommand {
        return UpdateAccountCommand(
            entityId = updateAccountMessage.id,
            balance = updateAccountMessage.balance,
            name = updateAccountMessage.name
        )
    }
}