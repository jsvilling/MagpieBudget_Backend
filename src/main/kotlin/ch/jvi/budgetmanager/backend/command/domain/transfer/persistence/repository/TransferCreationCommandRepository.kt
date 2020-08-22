package ch.jvi.budgetmanager.backend.command.domain.transfer.persistence.repository

import ch.jvi.budgetmanager.backend.command.domain.transfer.command.TransferCommand.CreateTransferCommand
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

/**
 * Repository for CreateTransferCommands
 *
 * @author J. Villing
 */
@Repository
interface TransferCreationCommandRepository : MongoRepository<CreateTransferCommand, String> {
    fun findByEntityId(entityId: String): CreateTransferCommand

}