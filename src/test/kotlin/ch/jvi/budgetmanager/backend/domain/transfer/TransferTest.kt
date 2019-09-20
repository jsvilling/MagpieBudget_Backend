package ch.jvi.budgetmanager.backend.domain.transfer

import ch.jvi.budgetmanager.backend.domain.transfer.TransferCommand.CreateTransferCommand
import org.junit.Test
import java.math.BigDecimal.TEN
import org.assertj.core.api.Assertions.assertThat as assertThat

internal class TransferTest {
    @Test
    fun testTransferCreation() {
        // Given
        val id = "123"
        val recipientId = "321"
        val senderId = "231"
        val amount = TEN
        val createTransferCommand = CreateTransferCommand(recipientId, senderId, amount, id)

        // When
        val transfer = Transfer(createTransferCommand)

        // Then
        assertThat(transfer).isEqualToComparingFieldByField(createTransferCommand)
    }
}