package ch.jvi.magpie.commandstore.account.persistance.repository

import ch.jvi.magpie.core.domain.account.AccountCommand.UpdateAccountCommand
import org.springframework.data.mongodb.repository.MongoRepository

/**
 * @author J. Villing
 */
interface UpdateAccountCommandRepository : MongoRepository<UpdateAccountCommand, String> {
    fun findByEntityId(entityId: String): List<UpdateAccountCommand>
}
