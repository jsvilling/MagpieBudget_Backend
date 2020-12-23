package ch.jvi.magpie.commandservice.transfer

import ch.jvi.magpie.domain.transfer.TransferEvent
import ch.jvi.magpie.eventbus.transfer.TransferEventListener
import org.assertj.core.api.Assertions
import org.junit.Ignore
import org.junit.Test
import java.math.BigDecimal


internal class TransferEventListenerTest {

    val transferEventListener = TransferEventListener()

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

        // When
        // Then
        Assertions.assertThatCode { transferEventListener.handle(createTransferEvent) }.doesNotThrowAnyException()
    }
}