package ch.jvi.magpie.commandservice.transfer

import ch.jvi.magpie.commandservice.EventListener
import ch.jvi.magpie.domain.domain.transfer.TransferEvent
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
