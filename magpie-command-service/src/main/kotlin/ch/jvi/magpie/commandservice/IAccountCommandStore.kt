package ch.jvi.magpie.commandservice

import ch.jvi.magpie.domain.account.AccountCommand

/**
 * @author J. Villing
 */
interface IAccountCommandStore : CommandStore<AccountCommand.CreateAccountCommand, AccountCommand>