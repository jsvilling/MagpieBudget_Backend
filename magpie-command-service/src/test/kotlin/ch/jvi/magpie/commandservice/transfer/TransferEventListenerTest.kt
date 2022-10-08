package ch.jvi.magpie.commandservice.transfer

import ch.jvi.magpie.domain.domain.transfer.TransferEvent
import jdk.nashorn.internal.ir.annotations.Ignore
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
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
