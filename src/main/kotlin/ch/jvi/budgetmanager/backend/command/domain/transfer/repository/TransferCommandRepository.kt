package ch.jvi.budgetmanager.backend.server.repository

import ch.jvi.budgetmanager.backend.command.domain.transfer.command.TransferCommand
import org.springframework.data.mongodb.repository.MongoRepository

/**
 * Repository for TransferCommands
 *
 * @author J. Villing
 */
interface TransferCommandRepository : MongoRepository<TransferCommand, String>