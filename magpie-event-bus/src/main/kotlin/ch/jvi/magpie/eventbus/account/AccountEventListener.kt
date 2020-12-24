package ch.jvi.magpie.eventbus.account

import ch.jvi.magpie.domain.account.AccountEvent
import ch.jvi.magpie.domain.account.IAccountService
import ch.jvi.magpie.domain.transfer.TransferEvent
import ch.jvi.magpie.domain.transfer.TransferEvent.CreateTransferEvent
import ch.jvi.magpie.eventbus.EventListener
import ch.jvi.magpie.queryservice.account.AccountQueryService
import ch.jvi.querydomain.account.QueryAccount
import org.springframework.stereotype.Component

@Component
class AccountEventListener(
    private val accountService: IAccountService,
    private val queryAccountService: AccountQueryService
) {

    @EventListener
    fun handle(createAccountEvent: AccountEvent.CreateAccountEvent) {
        queryAccountService.add(
            QueryAccount(
                id = createAccountEvent.id,
                balance = createAccountEvent.balance,
                name = createAccountEvent.name,
                transfers = mutableListOf()
            )
        )
    }

    @EventListener
    fun handle(updateAccountEvent: AccountEvent.UpdateAccountEvent) {
        queryAccountService.update(
            QueryAccount(
                id = updateAccountEvent.id,
                balance = updateAccountEvent.balance,
                name = updateAccountEvent.name,
                transfers = mutableListOf()
            )
        )
    }

    @EventListener
    fun handle(createTransferEvent: CreateTransferEvent) {
        accountService.updateAccountBalance(createTransferEvent.recipientId, createTransferEvent.amount)
        accountService.updateAccountBalance(createTransferEvent.senderId, createTransferEvent.amount.negate())

        queryAccountService.updateAccountBalance(createTransferEvent.recipientId, createTransferEvent.amount)
        queryAccountService.updateAccountBalance(createTransferEvent.senderId, createTransferEvent.amount.negate())
    }

    @EventListener
    fun handle(updateTransferEvent: TransferEvent.UpdateTransferEvent) {
        // Update Old Recipient
        accountService.updateAccountBalance(
            updateTransferEvent.oldRecipientId,
            updateTransferEvent.oldAmount.negate()
        )
        queryAccountService.updateAccountBalance(
            updateTransferEvent.oldRecipientId,
            updateTransferEvent.oldAmount.negate()
        )

        // Update Old Sender
        accountService.updateAccountBalance(
            updateTransferEvent.oldSenderId,
            updateTransferEvent.oldAmount
        )
        queryAccountService.updateAccountBalance(
            updateTransferEvent.oldSenderId,
            updateTransferEvent.oldAmount
        )

        // Update New Recipient
        accountService.updateAccountBalance(
            updateTransferEvent.newRecipientId,
            updateTransferEvent.newAmount
        )
        queryAccountService.updateAccountBalance(
            updateTransferEvent.newRecipientId,
            updateTransferEvent.newAmount
        )

        // Update New Sender
        accountService.updateAccountBalance(
            updateTransferEvent.newSenderId,
            updateTransferEvent.newAmount.negate()
        )
        queryAccountService.updateAccountBalance(
            updateTransferEvent.newSenderId,
            updateTransferEvent.newAmount.negate()
        )
    }

}