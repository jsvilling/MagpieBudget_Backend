package ch.jvi.budgetmanager.backend.server.repository

import ch.jvi.budgetmanager.backend.api.command.Command
import org.springframework.data.mongodb.repository.MongoRepository

interface UpdateCommandRepository : MongoRepository<Command, String>