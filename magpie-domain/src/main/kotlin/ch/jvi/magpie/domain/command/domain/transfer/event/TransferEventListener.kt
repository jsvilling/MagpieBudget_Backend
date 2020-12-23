package ch.jvi.magpie.domain.command.domain.transfer.event

import ch.jvi.magpie.domain.command.domain.transfer.event.TransferEvent.CreateTransferEvent
import ch.jvi.magpie.domain.command.domain.transfer.event.TransferEvent.UpdateTransferEvent
import ch.jvi.magpie.domain.event.api.EventListener
import org.springframework.stereotype.Component

@Component
class TransferEventListener {

    @EventListener
    fun handle(createTransferEvent: CreateTransferEvent) {
        // TODO: Use in query model to update query model
    }

    @EventListener
    fun handle(updateTransferEvent: UpdateTransferEvent) {
        // TODO: Use in query model to update query model
    }

}