package ch.jvi.magpie.commandstore.account.persistance.repository

import ch.jvi.magpie.core.domain.account.AccountCommand.AdjustAccountBalanceCommand
import org.springframework.data.mongodb.repository.MongoRepository

interface AdjustAccountBalanceCommandRepository : MongoRepository<AdjustAccountBalanceCommand, String> {
    fun findByEntityId(entityId: String): List<AdjustAccountBalanceCommand>
}
