package ch.jvi.magpie.domain.account

import ch.jvi.magpie.domain.CommandStore

/**
 * @author J. Villing
 */
interface IAccountCommandStore : CommandStore<AccountCommand.CreateAccountCommand, AccountCommand>
