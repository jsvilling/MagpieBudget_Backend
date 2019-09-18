package ch.jvi.budgetmanager.backend.core.message

import ch.jvi.budgetmanager.backend.api.command.CommandBus
import ch.jvi.budgetmanager.backend.api.command.CommandStore
import ch.jvi.budgetmanager.backend.domain.account.CreateAccountCommand
import ch.jvi.budgetmanager.backend.domain.account.UpdateAccountCommand
import org.junit.Test
import java.math.BigDecimal
import org.mockito.Mockito.*

internal class AccountMessageListenerTest {

    private val commandStore =mock(CommandStore::class.java)
    private val commandBus = mock(CommandBus::class.java)
    private val accountMessageListener = AccountMessageListener(commandStore, commandBus)

    @Test
    fun testHandleCreateAccountMessage() {
        // Given
        val balance = BigDecimal.TEN
        val name = "NameName"
        val receivedCreationMessage = AccountMessage.CreateAccountMessage(balance, name)
        val expectedCreationCommand = CreateAccountCommand(balance, name)

        // When
        accountMessageListener.handle(receivedCreationMessage)

        // Then
        verify(commandStore, times(1)).saveCreationCommand(expectedCreationCommand)
        verify(commandBus, times(1)).send(expectedCreationCommand)
    }

    @Test
    fun testHandleUpdateAccountMessage() {
        // Given
        val id = "id"
        val balance = BigDecimal.TEN
        val name = "NameName"
        val receivedUpdateMessage = AccountMessage.UpdateAccountMessage(id, balance, name)
        val expectedUpdateCommand = UpdateAccountCommand(balance, name, id)

        // When
        accountMessageListener.handle(receivedUpdateMessage)

        // Then
        verify(commandStore, times(1)).save(expectedUpdateCommand)
        verify(commandBus, times(1)).send(expectedUpdateCommand)
    }
}