package ch.jvi.magpie.core.domain.transfer

import ch.jvi.magpie.command.domain.transfer.Transfer
import ch.jvi.magpie.core.api.EntityService
import java.math.BigDecimal

interface ITransferService : EntityService<Transfer> {

    fun create(senderId: String, name: String, recipientId: String, amount: BigDecimal)

    fun update(updateTransferEvent: TransferEvent.UpdateTransferEvent)

    fun findAllForAccount(accountId: String): List<Transfer>

}
