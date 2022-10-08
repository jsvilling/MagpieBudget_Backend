package ch.jvi.magpie.core.domain.account

import ch.jvi.magpie.core.api.EntityService
import ch.jvi.magpie.core.domain.account.AccountCommand.CreateAccountCommand
import java.math.BigDecimal

/**
 * This service is used to validate input ralted to accounts and to create and send the corresponding Events.
 *
 * @author J. Villing
 */
interface IAccountService : EntityService<Account> {

    fun create(createAccountCommand: CreateAccountCommand)

    fun update(updateAccountCommand: AccountCommand.UpdateAccountCommand)

    fun updateAccountBalance(id: String, balanceChange: BigDecimal)

}
