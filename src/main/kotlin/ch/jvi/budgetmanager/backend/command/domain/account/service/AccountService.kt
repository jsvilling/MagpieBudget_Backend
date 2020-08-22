package ch.jvi.budgetmanager.backend.command.domain.account.service

import ch.jvi.budgetmanager.backend.command.api.event.EventBus
import ch.jvi.budgetmanager.backend.command.api.service.EntityService
import ch.jvi.budgetmanager.backend.command.domain.account.Account
import ch.jvi.budgetmanager.backend.command.domain.account.command.AccountCommand
import ch.jvi.budgetmanager.backend.command.domain.account.command.AccountCommand.CreateAccountCommand
import ch.jvi.budgetmanager.backend.command.domain.account.event.AccountEvent
import ch.jvi.budgetmanager.backend.command.domain.account.event.AccountEvent.CreateAccountEvent
import ch.jvi.budgetmanager.backend.command.domain.account.repository.AccountCommandRepository
import ch.jvi.budgetmanager.backend.command.domain.account.repository.AccountCreationCommandRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal

/**
 * This service is used to validate input ralted to accounts and to create and send the corresponding Events.
 *
 * @author J. Villing
 */
@Service
class AccountService(
    private val creationCommandRepository: AccountCreationCommandRepository,
    private val updateCommandRepository: AccountCommandRepository,
    private val eventBus: EventBus
) : EntityService<Account> {

    /**
     * @return The account with the requested ID
     * @throws IllegalArgumentException if no Entity with the given ID is found.
     */
    override fun find(entityId: String): Account {
        val creationCommand: CreateAccountCommand = creationCommandRepository.findByEntityId(entityId)
        val account = Account(creationCommand)
        return findAndApplyAllCommands(account)
    }

    override fun findAll(): List<Account> {
        return creationCommandRepository.findAll()
            .map { Account(it) }
            .map { findAndApplyAllCommands(it) }
    }

    private fun findAndApplyAllCommands(account: Account): Account {
        val commands: List<AccountCommand> = updateCommandRepository.findByEntityId(account.id)
        account.applyAll(commands)
        return account
    }

    /**
     * Creates and sends a CreateAccountEvent with the given balance and name
     */
    fun createAccount(balance: BigDecimal, name: String) {
        val createAccountCommand = CreateAccountCommand(balance, name)
        creationCommandRepository.save(createAccountCommand)

        val createAccountEvent = CreateAccountEvent(balance, name)
        eventBus.send(createAccountEvent)
    }

    /**
     * Creates and sends an UpdateAccountEvent with the given id, balance and name.
     */
    fun updateAccount(id: String, balance: BigDecimal, name: String) {
        val updateAccountCommand = AccountCommand.UpdateAccountCommand(balance, name, id)
        updateCommandRepository.save(updateAccountCommand)

        val updateAccountEvent = AccountEvent.UpdateAccountEvent(id, balance, name)
        eventBus.send(updateAccountEvent)
    }
}