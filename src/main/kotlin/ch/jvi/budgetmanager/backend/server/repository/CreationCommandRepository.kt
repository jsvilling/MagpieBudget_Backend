package ch.jvi.budgetmanager.backend.server.repository

import ch.jvi.budgetmanager.backend.api.command.CreationCommand
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface CreationCommandRepository : MongoRepository<CreationCommand, String>
