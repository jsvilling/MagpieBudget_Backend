package ch.jvi.magpie.eventbus.transfer

import ch.jvi.magpie.domain.transfer.TransferEvent
import ch.jvi.magpie.eventbus.EventListener
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