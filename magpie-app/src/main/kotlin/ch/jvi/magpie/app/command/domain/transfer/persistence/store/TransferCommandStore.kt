package ch.jvi.magpie.command.domain.transfer.persistence.store

import ch.jvi.magpie.command.api.CommandStore
import ch.jvi.magpie.command.domain.transfer.command.TransferCommand
import ch.jvi.magpie.command.domain.transfer.command.TransferCommand.CreateTransferCommand
import ch.jvi.magpie.command.domain.transfer.command.TransferCommand.UpdateTransferCommand
import ch.jvi.magpie.command.domain.transfer.persistence.repository.TransferCreationCommandRepository
import ch.jvi.magpie.command.domain.transfer.persistence.repository.UpdateTransferCommandRepository
import org.springframework.stereotype.Service

@Service
class TransferCommandStore(
    private val transferCreationCommandRepository: TransferCreationCommandRepository,
    private val updateTransferCommandRepository: UpdateTransferCommandRepository
) : CommandStore<CreateTransferCommand, TransferCommand> {

    override fun findCreationCommand(transferId: String): CreateTransferCommand {
        return transferCreationCommandRepository.findByEntityId(transferId)
    }

    override fun findAllCreationCommands(): List<CreateTransferCommand> {
        return transferCreationCommandRepository.findAll()
    }

    override fun findUpdateCommands(transferId: String): List<UpdateTransferCommand> {
        return updateTransferCommandRepository.findByEntityId(transferId)
    }

    fun findUpdateCommandsForAccount(accountId: String): List<UpdateTransferCommand> {
        return updateTransferCommandRepository.findByRecipientIdOrSenderId(accountId)
    }

    override fun save(command: TransferCommand) = when (command) {
        is CreateTransferCommand -> transferCreationCommandRepository.save(command)
        is UpdateTransferCommand -> updateTransferCommandRepository.save(command)
    }

}