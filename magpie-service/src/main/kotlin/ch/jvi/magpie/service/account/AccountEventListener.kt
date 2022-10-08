package ch.jvi.magpie.service.account

import ch.jvi.magpie.service.EventListener
import ch.jvi.magpie.core.domain.account.AccountEvent
import ch.jvi.magpie.core.domain.account.IAccountService
import ch.jvi.magpie.core.domain.transfer.TransferEvent
import ch.jvi.magpie.core.domain.transfer.TransferEvent.CreateTransferEvent
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionalEventListener

@Component
@Transactional(propagation = Propagation.MANDATORY)
class AccountEventListener(
    private val accountService: IAccountService
) {

    @EventListener
    fun handle(createAccountEvent: AccountEvent.CreateAccountEvent) {
        print("Received CreateAccountEvent $createAccountEvent")
    }

    @EventListener
    fun handle(updateAccountEvent: AccountEvent.UpdateAccountEvent) {
        print("Received UpdateAccountEvent $updateAccountEvent")
    }

    @TransactionalEventListener
    fun handle(createTransferEvent: CreateTransferEvent) {
        accountService.updateAccountBalance(createTransferEvent.recipientId, createTransferEvent.amount)
        accountService.updateAccountBalance(createTransferEvent.senderId, createTransferEvent.amount.negate())
    }

    @TransactionalEventListener
    fun handle(updateTransferEvent: TransferEvent.UpdateTransferEvent) {
        // TODO: Rethink - This is not efficient
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
