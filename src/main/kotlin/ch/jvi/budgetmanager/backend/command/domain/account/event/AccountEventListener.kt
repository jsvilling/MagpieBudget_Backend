package ch.jvi.budgetmanager.backend.command.domain.account.event

import ch.jvi.budgetmanager.backend.command.api.command.bus.CommandBus
import ch.jvi.budgetmanager.backend.command.domain.account.command.AccountCommand.CreateAccountCommand
import ch.jvi.budgetmanager.backend.command.domain.account.command.AccountCommand.UpdateAccountCommand
import ch.jvi.budgetmanager.backend.command.domain.account.repository.AccountCommandRepository
import ch.jvi.budgetmanager.backend.command.domain.account.repository.AccountCreationCommandRepository
import ch.jvi.budgetmanager.core.api.EventListener
import org.springframework.stereotype.Component

@Component
class AccountEventListener(
    private val creationCommandRepository: AccountCreationCommandRepository,
    private val updateCommandRepository: AccountCommandRepository,
    private val commandBus: CommandBus
) {

    @EventListener
    fun handle(createAccountEvent: AccountEvent.CreateAccountEvent) {
        val createAccountCommand: CreateAccountCommand = convertToCreationCommand(createAccountEvent)
        creationCommandRepository.save(createAccountCommand)
        commandBus.send(createAccountCommand)
    }

    private fun convertToCreationCommand(createAccountEvent: AccountEvent.CreateAccountEvent): CreateAccountCommand {
        return CreateAccountCommand(
            balance = createAccountEvent.balance,
            name = createAccountEvent.name
        )
    }

    @EventListener
    fun handle(updateAccountEvent: AccountEvent.UpdateAccountEvent) {
        val updateAccountCommand = convertToUpdateCommand(updateAccountEvent)
        updateCommandRepository.save(updateAccountCommand)
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