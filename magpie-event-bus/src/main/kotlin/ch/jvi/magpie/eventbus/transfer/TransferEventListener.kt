package ch.jvi.magpie.eventbus.transfer

import ch.jvi.magpie.domain.transfer.TransferEvent
import ch.jvi.magpie.eventbus.EventListener
import ch.jvi.magpie.query.transfer.TransferQueryService
import ch.jvi.magpie.queryservice.account.AccountQueryService
import ch.jvi.querydomain.transfer.QueryTransfer
import org.springframework.stereotype.Component

@Component
class TransferEventListener(
    val transferQueryService: TransferQueryService,
    val accountQueryService: AccountQueryService
) {

    @EventListener
    fun handle(createTransferEvent: TransferEvent.CreateTransferEvent) {
        transferQueryService.add(
            QueryTransfer(
                id = createTransferEvent.id,
                name = createTransferEvent.name,
                amount = createTransferEvent.amount,
                recipientId = createTransferEvent.recipientId,
                senderId = createTransferEvent.senderId,
                recipientName = accountQueryService.findAccountName(createTransferEvent.recipientId),
                senderName = accountQueryService.findAccountName(createTransferEvent.senderId)
            )
        )

    }

    @EventListener
    fun handle(updateTransferEvent: TransferEvent.UpdateTransferEvent) {
        transferQueryService.update(
            QueryTransfer(
                id = updateTransferEvent.transferId,
                name = updateTransferEvent.newName,
                amount = updateTransferEvent.newAmount,
                recipientId = updateTransferEvent.newRecipientId,
                senderId = updateTransferEvent.newSenderId,
                recipientName = accountQueryService.findAccountName(updateTransferEvent.newRecipientId),
                senderName = accountQueryService.findAccountName(updateTransferEvent.newSenderId)
            )
        )
    }

}