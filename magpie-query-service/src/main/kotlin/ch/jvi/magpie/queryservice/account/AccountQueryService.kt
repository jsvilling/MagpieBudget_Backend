package ch.jvi.magpie.query.account

import ch.jvi.magpie.domain.account.IAccountService
import ch.jvi.magpie.query.transfer.TransferQueryService
import ch.jvi.querydomain.account.QueryAccount
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AccountQueryService(
    private val accountService: IAccountService
) {

    @Autowired
    lateinit var transferQueryService: TransferQueryService

    fun find(entityId: String): QueryAccount {
        val account = accountService.find(entityId)
        val transfers = transferQueryService.findAllForAccount(entityId)
        return QueryAccount(account.id, account.name, account.balance, transfers)
    }

    fun findAll(): List<QueryAccount> {
        val allAccounts = accountService.findAll()
        return allAccounts.map {
            QueryAccount(it.id, it.name, it.balance, transferQueryService.findAllForAccount(it.id))
        }.toList()
    }

    fun findAccountName(entityId: String): String {
        return accountService.find(entityId).name
    }
}