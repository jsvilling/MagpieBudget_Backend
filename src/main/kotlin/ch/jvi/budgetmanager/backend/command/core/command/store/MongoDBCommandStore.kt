package ch.jvi.budgetmanager.backend.command.core.command.store

import ch.jvi.budgetmanager.backend.command.api.command.Command
import ch.jvi.budgetmanager.backend.command.api.command.CreationCommand
import ch.jvi.budgetmanager.backend.command.api.command.store.CommandStore
import ch.jvi.budgetmanager.backend.command.domain.account.AccountCommand
import ch.jvi.budgetmanager.backend.command.domain.transfer.TransferCommand
import ch.jvi.budgetmanager.backend.server.repository.CreationCommandRepository
import ch.jvi.budgetmanager.backend.server.repository.UpdateCommandRepository
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import kotlin.streams.toList

/**
 * CommandStore Implementation using MongoDB repositories.
 *
 * @author J. Villing
 */
@Service
@Profile("production")
class MongoDBCommandStore(
    private val creationCommandRepository: CreationCommandRepository,
    private val updateCommandRepository: UpdateCommandRepository
) : CommandStore {

    // TODO: Re-work repsository design to get rid of the nasty casts for entity specific commands.

    override fun find(entityId: String): List<Command> {
        return updateCommandRepository.findAll().stream()
            .filter { it.entityId == entityId }
            .toList()
    }

    override fun findAccountCommands(entityId: String): List<AccountCommand> {
        return find(entityId).filterIsInstance<AccountCommand>()
    }

    override fun findTransferCommands(entityId: String): List<TransferCommand> {
        return find(entityId).filterIsInstance<TransferCommand>()
    }

    override fun findCreationCommand(entityId: String): CreationCommand {
        return creationCommandRepository.findAll().stream().filter { it.entityId == entityId }
            .findFirst().orElseThrow { IllegalArgumentException() }
    }

    override fun findCreationCommands(predicate: (CreationCommand) -> Boolean): List<CreationCommand> {
        return creationCommandRepository.findAll().stream().filter(predicate).toList()
    }

    override fun save(command: Command) {
        updateCommandRepository.save(command)
    }

    override fun saveCreationCommand(command: CreationCommand) {
        creationCommandRepository.save(command)
    }
}