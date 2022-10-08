package ch.jvi.magpie.domain.domain.account

import ch.jvi.magpie.domain.api.CommandStore

/**
 * @author J. Villing
 */
interface IAccountCommandStore : CommandStore<AccountCommand.CreateAccountCommand, AccountCommand>
