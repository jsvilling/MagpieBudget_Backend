package ch.jvi.budgetmanager.backend.core.message

import ch.jvi.budgetmanager.backend.api.command.CommandBus
import ch.jvi.budgetmanager.backend.api.command.CommandStore
import org.junit.Test
import org.mockito.Mockito.*
import java.math.BigDecimal


internal class TransferMessageListenerTest {

    val commandBus = mock(CommandBus::class.java)
    val commandStore = mock(CommandStore::class.java)
    val transferMessageListener = TransferMessageListener(commandBus, commandStore)

    @Test
    fun testHandleCreateTransactionMessage() {
        // Given
        val recipientId = "0"
        val senderId = "0"
        val amount = BigDecimal.ONE
        val createTransferMessage = TransferMessage.CreateTransferMessage(recipientId, senderId, amount)

        // When
        transferMessageListener.handle(createTransferMessage)

        // Then
        // TODO: Assert correct commands were created persisted and sent over commandbus
    }
}