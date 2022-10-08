package ch.jvi.magpie.commandstore.account.persistance.repository

import ch.jvi.magpie.core.domain.account.AccountCommand.CreateAccountCommand
import org.springframework.data.mongodb.repository.MongoRepository

/**
 * @author J. Villing
 */
interface AccountCreationCommandRepository : MongoRepository<CreateAccountCommand, String> {
    fun findByEntityId(entityId: String): CreateAccountCommand
}
