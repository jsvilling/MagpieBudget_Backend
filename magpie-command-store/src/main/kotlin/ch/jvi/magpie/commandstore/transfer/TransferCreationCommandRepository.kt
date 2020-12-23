package ch.jvi.magpie.commandstore.transfer

import ch.jvi.magpie.domain.transfer.TransferCommand.CreateTransferCommand
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