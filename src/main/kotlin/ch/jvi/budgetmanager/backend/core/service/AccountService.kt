package ch.jvi.budgetmanager.backend.core.service

import ch.jvi.budgetmanager.backend.api.command.store.CommandStore
import ch.jvi.budgetmanager.backend.api.event.EventBus
import ch.jvi.budgetmanager.backend.api.service.EntityService
import ch.jvi.budgetmanager.backend.core.event.AccountEvent
import ch.jvi.budgetmanager.backend.domain.account.Account
import ch.jvi.budgetmanager.backend.domain.account.AccountCommand
import ch.jvi.budgetmanager.backend.domain.account.AccountCommand.CreateAccountCommand
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class AccountService(
    private val eventBus: EventBus,
    private val commandStore: CommandStore
) : EntityService<Account> {

    override fun find(entityId: String): Account {
        val creationCommand: CreateAccountCommand = commandStore.findCreationCommand(entityId) as CreateAccountCommand
        val commands: List<AccountCommand> = commandStore.findAccountCommands(entityId)
        val account = Account(creationCommand)
        account.applyAll(commands)
        return account
    }

    fun createAccount(balance: BigDecimal, name: String) {
        val createAccountMessage = AccountEvent.CreateAccountEvent(balance, name)
        eventBus.send(createAccountMessage)
    }

    fun updateAccount(id: String, balance: BigDecimal, name: String) {
        val updateAccountMessage = AccountEvent.UpdateAccountEvent(id, balance, name)
        eventBus.send(updateAccountMessage)
    }
}