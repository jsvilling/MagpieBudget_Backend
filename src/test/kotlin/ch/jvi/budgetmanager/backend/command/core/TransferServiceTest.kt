package ch.jvi.budgetmanager.backend.command.core

import ch.jvi.budgetmanager.backend.command.api.command.store.CommandStore
import ch.jvi.budgetmanager.backend.command.api.event.EventBus
import ch.jvi.budgetmanager.backend.command.domain.transfer.command.TransferCommand.CreateTransferCommand
import ch.jvi.budgetmanager.backend.command.domain.transfer.event.TransferEvent.CreateTransferEvent
import ch.jvi.budgetmanager.backend.command.domain.transfer.event.TransferEvent.UpdateTransferEvent
import ch.jvi.budgetmanager.backend.command.domain.transfer.service.TransferService
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.mockito.Mockito.*
import java.math.BigDecimal.TEN
import java.math.BigDecimal.ZERO

internal class TransferServiceTest {

    val commandStore = mock(CommandStore::class.java)
    val eventBus = mock(EventBus::class.java)

    private val transferService =
        TransferService(commandStore, eventBus)

    @Test
    fun testGetTransfer() {
        // Given
        val id = "someId"
        val creationCommand = CreateTransferCommand("reciient", "sender", "senderId", TEN, id)
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
        val creationCommand = CreateTransferEvent("name", recipientId, senderId, amount)

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
            UpdateTransferEvent(id, "", "", ZERO, recipientId, senderId, amount, senderId)

        // When
        transferService.updateTransfer(updateTransferEvent)

        // Then
        verify(eventBus, times(1)).send(updateTransferEvent)
    }
}