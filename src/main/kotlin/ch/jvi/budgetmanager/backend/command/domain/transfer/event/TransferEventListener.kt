package ch.jvi.budgetmanager.backend.command.domain.transfer.event

import ch.jvi.budgetmanager.backend.command.api.command.bus.CommandBus
import ch.jvi.budgetmanager.backend.command.domain.account.command.AccountCommand.AdjustAccountBalanceCommand
import ch.jvi.budgetmanager.backend.command.domain.account.repository.AccountCommandRepository
import ch.jvi.budgetmanager.backend.command.domain.transfer.command.TransferCommand.CreateTransferCommand
import ch.jvi.budgetmanager.backend.command.domain.transfer.command.TransferCommand.UpdateTransferCommand
import ch.jvi.budgetmanager.backend.command.domain.transfer.event.TransferEvent.CreateTransferEvent
import ch.jvi.budgetmanager.backend.command.domain.transfer.event.TransferEvent.UpdateTransferEvent
import ch.jvi.budgetmanager.backend.server.repository.TransferCommandRepository
import ch.jvi.budgetmanager.backend.server.repository.TransferCreationCommandRepository
import ch.jvi.budgetmanager.core.api.EventListener
import org.springframework.stereotype.Component

@Component
class TransferEventListener(
    private val commandBus: CommandBus,
    private val creationCommandRepository: TransferCreationCommandRepository,
    private val updateCommandRepository: TransferCommandRepository,

    // TODO: Move to account domain !
    private val updateAccountRepository: AccountCommandRepository
) {

    @EventListener
    fun handle(createTransferEvent: CreateTransferEvent) {
        val createTransferCommand = convertToCreateTransferCommand(createTransferEvent)
        val updateRecipientCommand = converToUpdateRecipientAccountCommand(createTransferEvent)
        val updateSenderAccount = converToUpdateSenderAccountCommand(createTransferEvent)
        commandBus.sendAll(
            listOf(
                createTransferCommand,
                updateRecipientCommand,
                updateSenderAccount
            )
        )
        creationCommandRepository.save(createTransferCommand)

        // TODO: Move to account domain !
        updateAccountRepository.saveAll(listOf(updateRecipientCommand, updateSenderAccount))
    }

    private fun convertToCreateTransferCommand(event: CreateTransferEvent): CreateTransferCommand {
        return CreateTransferCommand(
            name = event.name,
            recipientId = event.recipientId,
            senderId = event.senderId,
            amount = event.amount
        )
    }

    private fun converToUpdateRecipientAccountCommand(event: CreateTransferEvent): AdjustAccountBalanceCommand {
        return AdjustAccountBalanceCommand(
            entityId = event.recipientId,
            balanceChange = event.amount
        )
    }

    private fun converToUpdateSenderAccountCommand(event: CreateTransferEvent): AdjustAccountBalanceCommand {
        return AdjustAccountBalanceCommand(
            entityId = event.senderId,
            balanceChange = event.amount.negate()
        )
    }

    @EventListener
    fun handle(updateTransferEvent: UpdateTransferEvent) {
        val updateTransferCommand = convertToUpdateTransferCommand(updateTransferEvent)

        // Update Old Recipient
        val updateOldRecipientCommand = AdjustAccountBalanceCommand(
            entityId = updateTransferEvent.oldRecipientId,
            balanceChange = updateTransferEvent.oldAmount.negate()
        )

        // Update Old Sender
        val updateOldSenderCommand = AdjustAccountBalanceCommand(
            entityId = updateTransferEvent.oldSenderId,
            balanceChange = updateTransferEvent.oldAmount
        )

        // Update new Recipient
        val updateNewRecipientCommand = AdjustAccountBalanceCommand(
            entityId = updateTransferEvent.newRecipientId,
            balanceChange = updateTransferEvent.newAmount
        )

        // Update new Sender
        val updateNewSenderCommand = AdjustAccountBalanceCommand(
            entityId = updateTransferEvent.newSenderId,
            balanceChange = updateTransferEvent.newAmount.negate()
        )

        updateCommandRepository.save(updateTransferCommand)
        updateAccountRepository.saveAll(
            listOf(
                updateOldRecipientCommand,
                updateOldSenderCommand,
                updateNewRecipientCommand,
                updateNewSenderCommand
            )
        )

        commandBus.sendAll(
            listOf(
                updateTransferCommand,
                updateOldRecipientCommand,
                updateOldSenderCommand,
                updateNewRecipientCommand,
                updateNewSenderCommand
            )
        )
    }

    private fun convertToUpdateTransferCommand(event: UpdateTransferEvent): UpdateTransferCommand {
        return UpdateTransferCommand(
            entityId = event.transferId,
            recipientId = event.newRecipientId,
            senderId = event.newSenderId,
            amount = event.newAmount,
            name = event.newName
        )
    }

}