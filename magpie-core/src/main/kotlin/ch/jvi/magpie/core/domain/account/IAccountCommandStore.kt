package ch.jvi.magpie.core.domain.account

import ch.jvi.magpie.core.api.CommandStore

/**
 * @author J. Villing
 */
interface IAccountCommandStore : CommandStore<AccountCommand.CreateAccountCommand, AccountCommand>
