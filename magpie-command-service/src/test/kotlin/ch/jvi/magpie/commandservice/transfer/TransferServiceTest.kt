package ch.jvi.magpie.commandservice.transfer

import ch.jvi.magpie.commandservice.EventBus
import ch.jvi.magpie.commandservice.ITransferCommandStore
import ch.jvi.magpie.domain.transfer.TransferCommand
import ch.jvi.magpie.domain.transfer.TransferEvent
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.mockito.Mockito.*
import java.math.BigDecimal.TEN
import java.math.BigDecimal.ZERO

internal class TransferServiceTest {

    private val commandStore = mock(ITransferCommandStore::class.java)
    private val eventBus = mock(EventBus::class.java)

    private val transferService =
        TransferService(eventBus, commandStore)

    @Test
    fun testGetTransfer() {
        // Given
        val id = "someId"
        val creationCommand = TransferCommand.CreateTransferCommand("reciient", "sender", "senderId", TEN, id)
        `when`(commandStore.findCreationCommand(id)).thenReturn(creationCommand)

        // When
        val transfer = transferService.find(id)

        // Then
        verify(commandStore, times(1)).findCreationCommand(id)
        assertThat(transfer).isEqualToIgnoringGivenFields(creationCommand, "id", "creationCommand")
    }

    @Test
    fun testCreateTransfer() {
        // Given
        val recipientId = "reciient"
        val senderId = "sender"
        val amount = TEN
        val creationCommand = TransferEvent.CreateTransferEvent("name", recipientId, senderId, amount)

        // When
        transferService.createTransfer(senderId, "name", recipientId, amount)

        // Then
        verify(eventBus, times(1)).send(creationCommand)
    }

    @Test
    fun testUpdateTransfer() {
        // Given
        val id = "1"
        val recipientId = "reciient"
        val senderId = "sender"
        val amount = TEN
        val updateTransferEvent =
            TransferEvent.UpdateTransferEvent(id, "", "", ZERO, recipientId, senderId, amount, senderId)

        // When
        transferService.updateTransfer(updateTransferEvent)

        // Then
        verify(eventBus, times(1)).send(updateTransferEvent)
    }
}