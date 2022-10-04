package ch.jvi.magpie.commandservice.account

import ch.jvi.magpie.commandservice.EventBus
import ch.jvi.magpie.commandservice.IAccountCommandStore
import ch.jvi.magpie.domain.account.Account
import ch.jvi.magpie.domain.account.AccountCommand
import ch.jvi.magpie.domain.account.AccountCommand.*
import ch.jvi.magpie.domain.account.AccountEvent
import ch.jvi.magpie.domain.account.AccountEvent.CreateAccountEvent
import ch.jvi.magpie.domain.account.AccountEvent.UpdateAccountEvent
import ch.jvi.magpie.domain.account.IAccountService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

/**
 * This service is used to validate input ralted to accounts and to create and send the corresponding Events.
 *
 * @author J. Villing
 */
@Service
@Transactional(readOnly = true)
class AccountService(
    private val accountCommandStore: IAccountCommandStore,
    private val eventBus: EventBus
) : IAccountService {

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
            return account
        } catch (e: Exception) {
            throw IllegalStateException("Failed to apply commands for account $account.id")
        }
    }

    /**
     * Creates and sends a CreateAccountEvent with the given balance and name
     */
    @Transactional
    override fun createAccount(balance: BigDecimal, name: String) {
        val createAccountCommand = CreateAccountCommand(balance, name)
        val account = Account(createAccountCommand)
        accountCommandStore.save(createAccountCommand)

        val createAccountEvent = CreateAccountEvent(account.balance, account.name)
        eventBus.send(createAccountEvent)
    }

    /**
     * Creates and sends an UpdateAccountEvent with the given id, balance and name.
     */
    @Transactional
    override fun updateAccount(id: String, balance: BigDecimal, name: String) {
        val updateAccountCommand = UpdateAccountCommand(balance, name, id)
        val account = find(id);
        account.apply(updateAccountCommand)
        accountCommandStore.save(updateAccountCommand)

        val updateAccountEvent = UpdateAccountEvent(account.id, account.balance, account.name)
        eventBus.send(updateAccountEvent)
    }

    @Transactional
    override fun updateAccountBalance(id: String, balanceChange: BigDecimal) {
        val adjustAccountBalanceCommand = AdjustAccountBalanceCommand(balanceChange, id)
        val account = find(id);
        account.apply(adjustAccountBalanceCommand)
        accountCommandStore.save(adjustAccountBalanceCommand)

        val updateAccountEvent = UpdateAccountEvent(account.id, account.balance, account.name)
        eventBus.send(updateAccountEvent)
    }

}
