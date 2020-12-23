package ch.jvi.magpie.command.domain.transfer.event

import ch.jvi.budgetmanager.core.api.EventListener
import ch.jvi.magpie.command.domain.transfer.event.TransferEvent.CreateTransferEvent
import ch.jvi.magpie.command.domain.transfer.event.TransferEvent.UpdateTransferEvent
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