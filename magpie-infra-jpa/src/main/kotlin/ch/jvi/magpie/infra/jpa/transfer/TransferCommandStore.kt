package ch.jvi.magpie.infra.jpa.transfer

import ch.jvi.magpie.core.domain.transfer.ITransferCommandStore
import ch.jvi.magpie.core.domain.transfer.TransferCommand
import ch.jvi.magpie.core.domain.transfer.TransferCommand.CreateTransferCommand
import ch.jvi.magpie.core.domain.transfer.TransferCommand.UpdateTransferCommand
import org.springframework.stereotype.Service

@Service
class TransferCommandStore(
    private val transferCreationCommandRepository: TransferCreationCommandRepository,
    private val updateTransferCommandRepository: UpdateTransferCommandRepository
) : ITransferCommandStore {

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
