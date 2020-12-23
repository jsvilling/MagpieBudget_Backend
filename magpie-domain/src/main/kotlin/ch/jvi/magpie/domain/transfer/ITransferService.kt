package ch.jvi.magpie.domain.transfer

import ch.jvi.magpie.command.domain.transfer.Transfer
import ch.jvi.magpie.domain.EntityService
import java.math.BigDecimal

interface ITransferService : EntityService<Transfer> {

    fun createTransfer(senderId: String, name: String, recipientId: String, amount: BigDecimal)

    fun updateTransfer(updateTransferEvent: TransferEvent.UpdateTransferEvent)

    fun findAllForAccount(accountId: String): List<Transfer>

}