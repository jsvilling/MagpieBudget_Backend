package ch.jvi.magpie.infra.jpa.transfer

import ch.jvi.magpie.core.domain.transfer.TransferCommand.UpdateTransferCommand
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
