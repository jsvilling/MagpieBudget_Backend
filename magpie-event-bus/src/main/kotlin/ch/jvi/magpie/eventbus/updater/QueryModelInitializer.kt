package ch.jvi.magpie.eventbus.updater

import ch.jvi.magpie.commandservice.EventBus
import ch.jvi.magpie.domain.account.AccountEvent
import ch.jvi.magpie.domain.account.IAccountService
import ch.jvi.magpie.domain.transfer.ITransferService
import ch.jvi.magpie.domain.transfer.TransferEvent
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

/**
 * @author J. Villing
 */
@Component
class QueryModelInitializer(
    val accountService: IAccountService,
    val transferService: ITransferService,
    val eventBus: EventBus
) {

    @EventListener(ApplicationReadyEvent::class)
    fun initializeQueryModel() {
        initializeAccounts()
        initializeTransfers()
    }

    private fun initializeAccounts() {
        accountService.findAll()
            .map {
                AccountEvent.CreateAccountEvent(
                    id = it.id,
                    name = it.name,
                    balance = it.balance
                )
            }
            .forEach {
                eventBus.send(it)
            }
    }

    private fun initializeTransfers() {
        transferService.findAll()
            .map {
                TransferEvent.CreateTransferEvent(
                    id = it.id,
                    recipientId = it.recipientId,
                    senderId = it.senderId,
                    amount = it.amount,
                    name = it.name
                )
            }
            .forEach {
                eventBus.send(it)
            }
    }

}