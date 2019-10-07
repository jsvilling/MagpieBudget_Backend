package ch.jvi.budgetmanager.backend.core

import ch.jvi.budgetmanager.backend.api.command.store.CommandStore
import ch.jvi.budgetmanager.backend.api.event.EventBus
import ch.jvi.budgetmanager.backend.core.event.TransferEvent
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
        val creationCommand = TransferCommand.CreateTransferCommand("reciient", "sender", BigDecimal.TEN, id)
        Mockito.`when`(commandStore.findCreationCommand(id)).thenReturn(creationCommand)

        // When
        val transfer = transferService.getTransfer(id)

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
        val creationCommand = TransferEvent.CreateTransferEvent(recipientId, senderId, amount)

        // When
        transferService.createTransfer(senderId, recipientId, amount)

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
        val updateCommand = TransferEvent.UpdateTransferEvent(id, recipientId, senderId, amount)

        // When
        transferService.updateTransfer(id, senderId, recipientId, amount)

        // Then
        verify(messageBus, times(1)).send(updateCommand)
    }
}