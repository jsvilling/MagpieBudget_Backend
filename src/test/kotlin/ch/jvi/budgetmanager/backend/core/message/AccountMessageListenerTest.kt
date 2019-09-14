package ch.jvi.budgetmanager.backend.core.message

import ch.jvi.budgetmanager.backend.api.command.CommandBus
import ch.jvi.budgetmanager.backend.api.command.CommandStore
import ch.jvi.budgetmanager.backend.domain.account.CreateAccountCommand
import org.junit.Test
import org.mockito.Mockito
import java.math.BigDecimal

internal class AccountMessageListenerTest {

    private val commandStore = Mockito.mock(CommandStore::class.java)
    private val commandBus = Mockito.mock(CommandBus::class.java)
    private val accountMessageListener = AccountMessageListener(commandStore, commandBus)

    @Test
    fun testHandleCreateAccountMessage() {
        // Given
        val balance = BigDecimal.TEN
        val name = "NameName"
        val receivedCreationMessage = CreateAccountMessage(balance, name)
        val expectedCreationCommand = CreateAccountCommand(balance, name)

        // When
        accountMessageListener.handle(receivedCreationMessage)

        // Then
        Mockito.verify(commandStore, Mockito.times(1)).saveCreationCommand(expectedCreationCommand)
        Mockito.verify(commandBus, Mockito.times(1)).send(expectedCreationCommand)
    }
}