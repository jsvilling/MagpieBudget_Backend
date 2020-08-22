package ch.jvi.budgetmanager.backend.command.domain.transfer.persistence.repository

import ch.jvi.budgetmanager.backend.command.domain.transfer.command.TransferCommand.UpdateTransferCommand
import org.springframework.data.mongodb.repository.MongoRepository

/**
 * Repository for TransferCommands
 *
 * @author J. Villing
 */
interface UpdateTransferCommandRepository : MongoRepository<UpdateTransferCommand, String> {
    fun findByEntityId(entityId: String): List<UpdateTransferCommand>
    fun findByRecipientIdOrSenderId(accountId: String): List<UpdateTransferCommand>
}