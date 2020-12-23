package ch.jvi.magpie.domain.command.domain.account.service

import ch.jvi.magpie.domain.command.domain.account.Account
import ch.jvi.magpie.domain.command.domain.api.EntityService
import java.math.BigDecimal

/**
 * This service is used to validate input ralted to accounts and to create and send the corresponding Events.
 *
 * @author J. Villing
 */
interface IAccountService : EntityService<Account> {

    fun createAccount(balance: BigDecimal, name: String)

    fun updateAccount(id: String, balance: BigDecimal, name: String)

    fun updateAccountBalance(id: String, balanceChange: BigDecimal)

}