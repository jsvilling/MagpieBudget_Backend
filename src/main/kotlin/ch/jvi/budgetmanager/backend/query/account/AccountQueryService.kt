package ch.jvi.budgetmanager.backend.query.account

import ch.jvi.budgetmanager.backend.command.domain.account.service.AccountService
import ch.jvi.budgetmanager.backend.query.transfer.TransferQueryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AccountQueryService(
    private val accountService: AccountService
) {

    @Autowired
    lateinit var transferQueryService: TransferQueryService

    fun find(entityId: String): AccountDto {
        val account = accountService.find(entityId)
        val transfers = transferQueryService.findAllForAccount(entityId)
        return AccountDto(account.id, account.name, account.balance, transfers)
    }

    fun findAll(): List<AccountDto> {
        val allAccounts = accountService.findAll()
        return allAccounts.map {
            AccountDto(it.id, it.name, it.balance, transferQueryService.findAllForAccount(it.id))
        }.toList()
    }

    fun findAccountName(entityId: String): String {
        return accountService.find(entityId).name
    }
}