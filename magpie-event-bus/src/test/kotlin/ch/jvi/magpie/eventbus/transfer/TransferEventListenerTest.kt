package ch.jvi.magpie.eventbus.transfer

import ch.jvi.magpie.domain.transfer.TransferEvent
import ch.jvi.magpie.queryservice.account.AccountQueryService
import ch.jvi.magpie.queryservice.transfer.TransferQueryService
import org.assertj.core.api.Assertions
import org.junit.Ignore
import org.junit.Test
import org.mockito.Mockito
import java.math.BigDecimal


internal class TransferEventListenerTest {

    private val accountQueryService = Mockito.mock(AccountQueryService::class.java)
    private val transferQueryService = Mockito.mock(TransferQueryService::class.java)
    private val transferEventListener = TransferEventListener(transferQueryService, accountQueryService)

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
                "id",
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