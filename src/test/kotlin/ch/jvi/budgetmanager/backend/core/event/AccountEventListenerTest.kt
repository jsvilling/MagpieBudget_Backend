package ch.jvi.budgetmanager.backend.core.event

import ch.jvi.budgetmanager.backend.api.command.bus.CommandBus
import ch.jvi.budgetmanager.backend.api.command.store.CommandStore
import ch.jvi.budgetmanager.backend.domain.account.AccountCommand.CreateAccountCommand
import ch.jvi.budgetmanager.backend.domain.account.AccountCommand.UpdateAccountCommand
import org.junit.Test
import org.mockito.Mockito.*
import java.math.BigDecimal

internal class AccountEventListenerTest {

    private val commandStore =mock(CommandStore::class.java)
    private val commandBus = mock(CommandBus::class.java)
    private val accountMessageListener = AccountEventListener(commandStore, commandBus)

    @Test
    fun testHandleCreateAccountMessage() {
        // Given
        val balance = BigDecimal.TEN
        val name = "NameName"
        val receivedCreationMessage = AccountEvent.CreateAccountEvent(balance, name)
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
        val receivedUpdateMessage = AccountEvent.UpdateAccountEvent(id, balance, name)
        val expectedUpdateCommand = UpdateAccountCommand(balance, name, id)

        // When
        accountMessageListener.handle(receivedUpdateMessage)

        // Then
        verify(commandStore, times(1)).save(expectedUpdateCommand)
        verify(commandBus, times(1)).send(expectedUpdateCommand)
    }
}