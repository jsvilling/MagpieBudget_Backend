package ch.jvi.budgetmanager.backend.core

import ch.jvi.budgetmanager.backend.api.command.store.CommandStore
import ch.jvi.budgetmanager.backend.api.event.EventBus
import ch.jvi.budgetmanager.backend.core.event.TransferEvent
import ch.jvi.budgetmanager.backend.core.service.TransferService
import ch.jvi.budgetmanager.backend.domain.transfer.TransferCommand
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import java.math.BigDecimal

internal class TransferServiceTest {

    val commandStore = Mockito.mock(CommandStore::class.java)
    val messageBus = Mockito.mock(EventBus::class.java)

    private val transferService = TransferService(commandStore, messageBus)

    @Test
    fun testGetTransfer() {
        // Given
        val id = "someId"
        val creationCommand =
            TransferCommand.CreateTransferCommand("reciient", "sender", "senderId", BigDecimal.TEN, id)
        Mockito.`when`(commandStore.findCreationCommand(id)).thenReturn(creationCommand)

        // When
        val transfer = transferService.find(id)

        // Then
        verify(commandStore, times(1)).findCreationCommand(id)
        verify(commandStore, times(1)).findTransferCommands(id)
        assertThat(transfer).isEqualToComparingFieldByField(creationCommand)
    }

    @Test
    fun testCreateTransfer() {
        // Given
        val id = "1"
        val recipientId = "reciient"
        val senderId = "sender"
        val amount = BigDecimal.TEN
        val budgetId = "123"
        val creationCommand = TransferEvent.CreateTransferEvent("name", recipientId, senderId, amount, budgetId)

        // When
        transferService.createTransfer(senderId, "name", recipientId, amount, budgetId)

        // Then
        verify(messageBus, times(1)).send(creationCommand)
    }

    @Test
    fun testUpdateTransfer() {
        // Given
        val id = "1"
        val recipientId = "reciient"
        val senderId = "sender"
        val amount = BigDecimal.TEN
        val updateTransferEvent = TransferEvent.UpdateTransferEvent(
            id, "", "",
            BigDecimal.ZERO, recipientId, senderId, amount, senderId
        )

        // When
        transferService.updateTransfer(updateTransferEvent)

        // Then
        verify(messageBus, times(1)).send(updateTransferEvent)
    }
}