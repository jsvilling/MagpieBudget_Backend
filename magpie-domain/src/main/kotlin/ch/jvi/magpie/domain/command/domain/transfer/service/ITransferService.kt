package ch.jvi.magpie.domain.command.domain.transfer.service

import ch.jvi.magpie.command.domain.transfer.Transfer
import ch.jvi.magpie.domain.command.domain.api.EntityService
import ch.jvi.magpie.domain.command.domain.transfer.event.TransferEvent.UpdateTransferEvent
import java.math.BigDecimal

interface ITransferService : EntityService<Transfer> {

    fun createTransfer(senderId: String, name: String, recipientId: String, amount: BigDecimal)

    fun updateTransfer(updateTransferEvent: UpdateTransferEvent)

    fun findAllForAccount(accountId: String): List<Transfer>

}