package ch.jvi.budgetmanager.backend.command.domain.account.event

import ch.jvi.budgetmanager.backend.command.api.command.CreationCommand
import ch.jvi.budgetmanager.backend.command.api.command.bus.CommandBus
import ch.jvi.budgetmanager.backend.command.api.command.store.CommandStore
import ch.jvi.budgetmanager.backend.command.domain.account.command.AccountCommand
import ch.jvi.budgetmanager.backend.command.domain.account.command.AccountCommand.UpdateAccountCommand
import ch.jvi.budgetmanager.core.api.EventListener
import org.springframework.stereotype.Component

@Component
class AccountEventListener(
    private val commandStore: CommandStore,
    private val commandBus: CommandBus
) {

    @EventListener
    fun handle(createAccountEvent: AccountEvent.CreateAccountEvent) {
        val createAccountCommand = convertToCreationCommand(createAccountEvent)
        commandStore.saveCreationCommand(createAccountCommand)
        commandBus.send(createAccountCommand)
    }

    private fun convertToCreationCommand(createAccountEvent: AccountEvent.CreateAccountEvent): CreationCommand {
        return AccountCommand.CreateAccountCommand(
            balance = createAccountEvent.balance,
            name = createAccountEvent.name
        )
    }

    @EventListener
    fun handle(updateAccountEvent: AccountEvent.UpdateAccountEvent) {
        val updateAccountCommand = convertToUpdateCommand(updateAccountEvent)
        commandStore.save(updateAccountCommand)
        commandBus.send(updateAccountCommand)
    }

    fun convertToUpdateCommand(updateAccountEvent: AccountEvent.UpdateAccountEvent): UpdateAccountCommand {
        return UpdateAccountCommand(
            entityId = updateAccountEvent.id,
            balance = updateAccountEvent.balance,
            name = updateAccountEvent.name
        )
    }
}