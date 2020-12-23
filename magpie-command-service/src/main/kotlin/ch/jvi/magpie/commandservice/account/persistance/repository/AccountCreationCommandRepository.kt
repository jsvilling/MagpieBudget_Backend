package ch.jvi.magpie.commandservice.account.persistance.repository

import ch.jvi.magpie.domain.account.command.AccountCommand.CreateAccountCommand
import org.springframework.data.mongodb.repository.MongoRepository

/**
 * @author J. Villing
 */
interface AccountCreationCommandRepository : MongoRepository<CreateAccountCommand, String> {
    fun findByEntityId(entityId: String): CreateAccountCommand
}