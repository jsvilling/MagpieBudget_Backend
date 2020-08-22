package ch.jvi.budgetmanager.backend.command.domain.account.persistance.store

import ch.jvi.budgetmanager.backend.command.api.CommandStore
import ch.jvi.budgetmanager.backend.command.domain.account.command.AccountCommand
import ch.jvi.budgetmanager.backend.command.domain.account.command.AccountCommand.CreateAccountCommand
import ch.jvi.budgetmanager.backend.command.domain.account.persistance.repository.AccountCreationCommandRepository
import ch.jvi.budgetmanager.backend.command.domain.account.persistance.repository.AdjustAccountBalanceCommandRepository
import ch.jvi.budgetmanager.backend.command.domain.account.persistance.repository.UpdateAccountCommandRepository
import org.springframework.stereotype.Service

@Service
class AccountCommandStore(
    private val accountCreationCommandRepository: AccountCreationCommandRepository,
    private val updateAccountCommandRepository: UpdateAccountCommandRepository,
    private val adjustAccountBalanceCommandRepository: AdjustAccountBalanceCommandRepository
) : CommandStore<CreateAccountCommand, AccountCommand> {

    override fun findCreationCommand(accountId: String): CreateAccountCommand {
        return accountCreationCommandRepository.findByEntityId(accountId)
    }

    override fun findAllCreationCommands(): List<CreateAccountCommand> {
        return accountCreationCommandRepository.findAll()
    }

    override fun findUpdateCommands(accountId: String): List<AccountCommand> {
        return listOf(
            updateAccountCommandRepository.findByEntityId(accountId),
            adjustAccountBalanceCommandRepository.findByEntityId(accountId)
        ).flatten()
    }

    override fun save(command: AccountCommand) = when (command) {
        is CreateAccountCommand -> accountCreationCommandRepository.save(command)
        is AccountCommand.UpdateAccountCommand -> updateAccountCommandRepository.save(command)
        is AccountCommand.AdjustAccountBalanceCommand -> adjustAccountBalanceCommandRepository.save(command)
    }

}