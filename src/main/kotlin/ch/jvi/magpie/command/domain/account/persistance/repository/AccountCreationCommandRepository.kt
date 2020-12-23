package ch.jvi.magpie.command.domain.account.persistance.repository

import ch.jvi.magpie.command.domain.account.command.AccountCommand.CreateAccountCommand
import org.springframework.data.mongodb.repository.MongoRepository

/**
 * @author J. Villing
 */
interface AccountCreationCommandRepository : MongoRepository<CreateAccountCommand, String> {
    fun findByEntityId(entityId: String): CreateAccountCommand
}