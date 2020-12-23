package ch.jvi.magpie.domain.command.domain.account.persistance.repository

import ch.jvi.magpie.domain.command.domain.account.command.AccountCommand.AdjustAccountBalanceCommand
import org.springframework.data.mongodb.repository.MongoRepository

interface AdjustAccountBalanceCommandRepository : MongoRepository<AdjustAccountBalanceCommand, String> {
    fun findByEntityId(entityId: String): List<AdjustAccountBalanceCommand>
}