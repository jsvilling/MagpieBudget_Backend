package ch.jvi.magpie.domain.transfer

import ch.jvi.magpie.domain.event.api.EventListener
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