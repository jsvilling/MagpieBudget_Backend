package ch.jvi.magpie.domain.domain.transfer

import ch.jvi.magpie.command.domain.transfer.Transfer
import ch.jvi.magpie.domain.api.EntityService
import java.math.BigDecimal

interface ITransferService : EntityService<Transfer> {

    fun create(senderId: String, name: String, recipientId: String, amount: BigDecimal)

    fun update(updateTransferEvent: TransferEvent.UpdateTransferEvent)

    fun findAllForAccount(accountId: String): List<Transfer>

}
