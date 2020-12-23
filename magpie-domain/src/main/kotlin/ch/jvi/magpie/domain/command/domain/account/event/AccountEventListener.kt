package ch.jvi.magpie.domain.command.domain.account.event

import ch.jvi.magpie.domain.command.domain.account.service.IAccountService
import ch.jvi.magpie.domain.command.domain.transfer.event.TransferEvent
import ch.jvi.magpie.domain.command.domain.transfer.event.TransferEvent.CreateTransferEvent
import ch.jvi.magpie.domain.event.api.EventListener
import org.springframework.stereotype.Component

@Component
class AccountEventListener(
    private val accountService: IAccountService
) {

    @EventListener
    fun handle(createAccountEvent: AccountEvent.CreateAccountEvent) {
        // TODO: Use in query model to update query model
    }

    @EventListener
    fun handle(updateAccountEvent: AccountEvent.UpdateAccountEvent) {
        // TODO: Use in query model to update query model
    }

    @EventListener
    fun handle(createTransferEvent: CreateTransferEvent) {
        accountService.updateAccountBalance(createTransferEvent.recipientId, createTransferEvent.amount)
        accountService.updateAccountBalance(createTransferEvent.senderId, createTransferEvent.amount.negate())
    }

    @EventListener
    fun handle(updateTransferEvent: TransferEvent.UpdateTransferEvent) {
        // Update Old Recipient
        accountService.updateAccountBalance(
            updateTransferEvent.oldRecipientId,
            updateTransferEvent.oldAmount.negate()
        )

        // Update Old Sender
        accountService.updateAccountBalance(
            updateTransferEvent.oldSenderId,
            updateTransferEvent.oldAmount
        )

        // Update New Recipient
        accountService.updateAccountBalance(
            updateTransferEvent.newRecipientId,
            updateTransferEvent.newAmount
        )

        // Update New Sender
        accountService.updateAccountBalance(
            updateTransferEvent.newSenderId,
            updateTransferEvent.newAmount.negate()
        )
    }

}