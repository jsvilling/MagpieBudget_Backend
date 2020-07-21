package ch.jvi.budgetmanager.backend.server.repository

import ch.jvi.budgetmanager.backend.command.api.command.Command
import org.springframework.data.mongodb.repository.MongoRepository

/**
 * Repository for Updatecommands
 *
 * @author J. Villing
 */
interface UpdateCommandRepository : MongoRepository<Command, String>