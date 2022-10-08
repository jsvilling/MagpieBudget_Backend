package ch.jvi.magpie.commandstore.account.persistance.store

import ch.jvi.magpie.core.domain.account.IAccountCommandStore
import ch.jvi.magpie.commandstore.account.persistance.repository.AccountCreationCommandRepository
import ch.jvi.magpie.commandstore.account.persistance.repository.AdjustAccountBalanceCommandRepository
import ch.jvi.magpie.commandstore.account.persistance.repository.UpdateAccountCommandRepository
import ch.jvi.magpie.core.domain.account.AccountCommand
import ch.jvi.magpie.core.domain.account.AccountCommand.CreateAccountCommand
import org.springframework.stereotype.Service

@Service
class AccountCommandStore(
    private val accountCreationCommandRepository: AccountCreationCommandRepository,
    private val updateAccountCommandRepository: UpdateAccountCommandRepository,
    private val adjustAccountBalanceCommandRepository: AdjustAccountBalanceCommandRepository
) : IAccountCommandStore {

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
