package ch.jvi.magpie.service.transfer

import ch.jvi.magpie.service.EventListener
import ch.jvi.magpie.core.domain.transfer.TransferEvent
import org.springframework.stereotype.Component

@Component
class TransferEventListener {

    @EventListener
    fun handle(createTransferEvent: TransferEvent.CreateTransferEvent) {
        // TODO: Use in query model to update query model
    }

    @EventListener
    fun handle(updateTransferEvent: TransferEvent.UpdateTransferEvent) {
        // TODO: Use in query model to update query model
    }

}
