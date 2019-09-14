package ch.jvi.budgetmanager.backend.core

import ch.jvi.budgetmanager.backend.api.command.CommandStore
import ch.jvi.budgetmanager.backend.core.message.CreateAccountMessage
import ch.jvi.budgetmanager.backend.domain.account.Account
import ch.jvi.budgetmanager.backend.domain.account.CreateAccountCommand
import ch.jvi.budgetmanager.backend.api.message.MessageBus
import org.springframework.stereotype.Service
import java.math.BigDecimal


@Service
class AccountService(private val messageBus: MessageBus, private val commandStore: CommandStore) {

    fun getAccount(id: String): Account {
        val creationCommand: CreateAccountCommand = commandStore.findCreationCommand(id) as CreateAccountCommand
        return Account(creationCommand)
    }

    fun createAccount(balance: BigDecimal, name: String) {
        val createAccountMessage = CreateAccountMessage(balance, name)
        messageBus.send(createAccountMessage)
    }
}