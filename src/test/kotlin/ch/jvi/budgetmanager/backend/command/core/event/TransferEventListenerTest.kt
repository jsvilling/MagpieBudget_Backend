package ch.jvi.budgetmanager.backend.command.core.event

import ch.jvi.budgetmanager.backend.command.api.command.bus.CommandBus
import ch.jvi.budgetmanager.backend.command.api.command.store.CommandStore
import ch.jvi.budgetmanager.backend.command.domain.account.AccountCommand
import ch.jvi.budgetmanager.backend.command.domain.transfer.TransferCommand.CreateTransferCommand
import org.junit.Ignore
import org.junit.Test
import org.mockito.Mockito.*
import java.math.BigDecimal


internal class TransferEventListenerTest {

    val commandBus = mock(CommandBus::class.java)
    val commandStore = mock(CommandStore::class.java)
    val transferEventListener =
        TransferEventListener(
            commandBus,
            commandStore
        )

    // Ignored until mockito-kotlin is integrated
    @Test
    @Ignore
    fun testHandleCreateTransactionEvent() {
        // Given
        val recipientId = "0"
        val senderId = "1"
        val amount = BigDecimal.ONE
        val createTransferEvent =
            TransferEvent.CreateTransferEvent(
                "name",
                recipientId,
                senderId,
                amount
            )
        val createTransferCommand = CreateTransferCommand("name", recipientId, senderId, amount, "0")
        val adjustRecipientCommand = AccountCommand.AdjustAccountBalanceCommand(amount, recipientId)
        val adjustSenderCommand = AccountCommand.AdjustAccountBalanceCommand(amount.negate(), senderId)

        // When
        transferEventListener.handle(createTransferEvent)

        // Then
        verify(commandBus, times(1)).sendAll(listOf(createTransferCommand, adjustRecipientCommand, adjustSenderCommand))
        verify(commandStore, times(1)).saveAll(listOf(adjustRecipientCommand, adjustSenderCommand))
    }
}