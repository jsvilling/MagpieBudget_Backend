package ch.jvi.budgetmanager.backend.server.repository

import ch.jvi.budgetmanager.backend.command.api.command.CreationCommand
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

/**
 * Repository for CreationCommands
 *
 * @author J. Villing
 */
@Repository
interface CreationCommandRepository : MongoRepository<CreationCommand, String>
