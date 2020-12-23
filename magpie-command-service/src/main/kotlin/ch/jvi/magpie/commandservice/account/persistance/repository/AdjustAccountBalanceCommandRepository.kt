package ch.jvi.magpie.commandservice.account.persistance.repository

import ch.jvi.magpie.domain.account.command.AccountCommand.AdjustAccountBalanceCommand
import org.springframework.data.mongodb.repository.MongoRepository

interface AdjustAccountBalanceCommandRepository : MongoRepository<AdjustAccountBalanceCommand, String> {
    fun findByEntityId(entityId: String): List<AdjustAccountBalanceCommand>
}