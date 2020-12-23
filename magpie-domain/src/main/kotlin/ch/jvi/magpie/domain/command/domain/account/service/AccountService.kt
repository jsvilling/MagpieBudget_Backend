package ch.jvi.magpie.domain.command.domain.account.service

import ch.jvi.magpie.domain.command.domain.account.Account
import ch.jvi.magpie.domain.command.domain.account.command.AccountCommand
import ch.jvi.magpie.domain.command.domain.account.command.AccountCommand.CreateAccountCommand
import ch.jvi.magpie.domain.command.domain.account.command.AccountCommand.UpdateAccountCommand
import ch.jvi.magpie.domain.command.domain.account.event.AccountEvent
import ch.jvi.magpie.domain.command.domain.account.event.AccountEvent.CreateAccountEvent
import ch.jvi.magpie.domain.command.domain.account.persistance.store.AccountCommandStore
import ch.jvi.magpie.domain.command.domain.api.EntityService
import ch.jvi.magpie.domain.event.api.EventBus
import org.springframework.stereotype.Service
import java.math.BigDecimal

/**
 * This service is used to validate input ralted to accounts and to create and send the corresponding Events.
 *
 * @author J. Villing
 */
@Service
class AccountService(
    private val accountCommandStore: AccountCommandStore,
    private val eventBus: EventBus
) : EntityService<Account> {

    /**
     * @return The account with the requested ID
     * @throws IllegalArgumentException if no Entity with the given ID is found.
     */
    override fun find(entityId: String): Account {
        val creationCommand: CreateAccountCommand = accountCommandStore.findCreationCommand(entityId)
        val account = Account(creationCommand)
        return findAndApplyAllCommands(account)
    }

    override fun findAll(): List<Account> {
        return accountCommandStore.findAllCreationCommands()
            .map { Account(it) }
            .map { findAndApplyAllCommands(it) }
    }

    private fun findAndApplyAllCommands(account: Account): Account {
        try {
            val commands: List<AccountCommand> = accountCommandStore.findUpdateCommands(account.id)
            account.applyAll(commands)
        } catch (e: Exception) {

        }
        return account
    }

    /**
     * Creates and sends a CreateAccountEvent with the given balance and name
     */
    fun createAccount(balance: BigDecimal, name: String) {
        val createAccountCommand = CreateAccountCommand(balance, name)
        accountCommandStore.save(createAccountCommand)

        val createAccountEvent = CreateAccountEvent(balance, name)
        eventBus.send(createAccountEvent)
    }

    /**
     * Creates and sends an UpdateAccountEvent with the given id, balance and name.
     */
    fun updateAccount(id: String, balance: BigDecimal, name: String) {
        val updateAccountCommand = UpdateAccountCommand(balance, name, id)
        accountCommandStore.save(updateAccountCommand)

        val updateAccountEvent = AccountEvent.UpdateAccountEvent(id, balance, name)
        eventBus.send(updateAccountEvent)
    }

    fun updateAccountBalance(id: String, balanceChange: BigDecimal) {
        val updateCommand = AccountCommand.AdjustAccountBalanceCommand(balanceChange, id)
        accountCommandStore.save(updateCommand)
    }

}