package ch.jvi.budgetmanager.backend.command.domain.account.repository

import ch.jvi.budgetmanager.backend.command.domain.account.command.AccountCommand
import org.springframework.data.mongodb.repository.MongoRepository

/**
 * @author J. Villing
 */
interface AccountCommandRepository : MongoRepository<AccountCommand, String>