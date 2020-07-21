package ch.jvi.budgetmanager.backend.command.core.event

import ch.jvi.budgetmanager.backend.command.api.command.CreationCommand
import ch.jvi.budgetmanager.backend.command.api.command.bus.CommandBus
import ch.jvi.budgetmanager.backend.command.api.command.store.CommandStore
import ch.jvi.budgetmanager.backend.command.domain.account.AccountCommand
import ch.jvi.budgetmanager.backend.command.domain.account.AccountCommand.UpdateAccountCommand
import ch.jvi.budgetmanager.core.api.EventListener
import org.springframework.stereotype.Component

@Component
class AccountEventListener(
    private val commandStore: CommandStore,
    private val commandBus: CommandBus
) {

    @EventListener
    fun handle(createAccountMessage: AccountEvent.CreateAccountEvent) {
        val createAccountCommand = convertToCreationCommand(createAccountMessage)
        commandStore.saveCreationCommand(createAccountCommand)
        commandBus.send(createAccountCommand)
    }

    private fun convertToCreationCommand(createAccountMessage: AccountEvent.CreateAccountEvent): CreationCommand {
        return AccountCommand.CreateAccountCommand(
            balance = createAccountMessage.balance,
            name = createAccountMessage.name
        )
    }

    @EventListener
    fun handle(updateAccountMessage: AccountEvent.UpdateAccountEvent) {
        val updateAccountCommand = convertToUpdateCommand(updateAccountMessage)
        commandStore.save(updateAccountCommand)
        commandBus.send(updateAccountCommand)
    }

    fun convertToUpdateCommand(updateAccountMessage: AccountEvent.UpdateAccountEvent): UpdateAccountCommand {
        return UpdateAccountCommand(
            entityId = updateAccountMessage.id,
            balance = updateAccountMessage.balance,
            name = updateAccountMessage.name
        )
    }
}