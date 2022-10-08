package ch.jvi.magpie.queryservice.account

import ch.jvi.magpie.domain.account.IAccountService
import ch.jvi.magpie.query.account.AccountDto
import ch.jvi.magpie.queryservice.transfer.TransferQueryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
@Service
@Deprecated("This is just a loose draft for testing out some things, do not use it")
class AccountQueryService(
    private val accountService: IAccountService
) {

    @Autowired
    lateinit var transferQueryService: TransferQueryService

    fun find(entityId: String): AccountDto {
        val account = accountService.find(entityId)
        val transfers = transferQueryService.findAllForAccount(entityId)
        return AccountDto(account.id, account.name, account.balance, transfers)
    }

    fun findAll(): List<AccountDto> {
        return accountService
                .findAll()
                .map {
                    AccountDto(it.id, it.name, it.balance, transferQueryService.findAllForAccount(it.id))
                }
    }

    fun findAccountName(entityId: String): String {
        return accountService.find(entityId).name
    }
}
