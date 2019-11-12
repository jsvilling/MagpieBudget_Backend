package ch.jvi.budgetmanager.backend.core.event

import ch.jvi.budgetmanager.backend.api.command.bus.CommandBus
import ch.jvi.budgetmanager.backend.api.command.store.CommandStore
import ch.jvi.budgetmanager.backend.domain.account.AccountCommand
import ch.jvi.budgetmanager.backend.domain.transfer.TransferCommand.CreateTransferCommand
import org.junit.Test
import org.mockito.Mockito.*
import java.math.BigDecimal


internal class TransferEventListenerTest {

    val commandBus = mock(CommandBus::class.java)
    val commandStore = mock(CommandStore::class.java)
    val transferMessageListener = TransferEventListener(commandBus, commandStore)

    @Test
    fun testHandleCreateTransactionMessage() {
        // Given
        val recipientId = "0"
        val senderId = "1"
        val amount = BigDecimal.ONE
        val budgetId = "2"
        val createTransferMessage = TransferEvent.CreateTransferEvent(recipientId, senderId, amount, budgetId)
        val createTransferCommand = CreateTransferCommand(recipientId, senderId, amount, "0")
        val adjustRecipientCommand = AccountCommand.AdjustAccountBalanceCommand(amount, recipientId)
        val adjustSenderCommand = AccountCommand.AdjustAccountBalanceCommand(amount.negate(), senderId)

        // When
        transferMessageListener.handle(createTransferMessage)

        // Then
        verify(commandBus, times(1)).sendAll(listOf(createTransferCommand, adjustRecipientCommand, adjustSenderCommand))
        verify(commandStore, times(1)).saveAll(listOf(adjustRecipientCommand, adjustSenderCommand))
    }
}