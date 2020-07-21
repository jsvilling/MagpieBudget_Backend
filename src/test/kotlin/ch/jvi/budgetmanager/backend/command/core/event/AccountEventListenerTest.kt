package ch.jvi.budgetmanager.backend.command.core.event

import ch.jvi.budgetmanager.backend.command.api.command.bus.CommandBus
import ch.jvi.budgetmanager.backend.command.api.command.store.CommandStore
import ch.jvi.budgetmanager.backend.command.domain.IDProvider
import ch.jvi.budgetmanager.backend.command.domain.account.AccountCommand.CreateAccountCommand
import ch.jvi.budgetmanager.backend.command.domain.account.AccountCommand.UpdateAccountCommand
import org.junit.Ignore
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.*
import java.math.BigDecimal

internal class AccountEventListenerTest {

    private val commandStore = mock(CommandStore::class.java)
    private val commandBus = mock(CommandBus::class.java)
    private val accountMessageListener =
        AccountEventListener(
            commandStore,
            commandBus
        )
    private val captor = ArgumentCaptor.forClass(UpdateAccountCommand::class.java)

    // Ignored until mockito-kotlin is integrated
    @Test
    @Ignore
    fun testHandleCreateAccountMessage() {
        // Given
        val balance = BigDecimal.TEN
        val name = "NameName"
        val accountId = IDProvider.nextId
        val receivedCreationMessage =
            AccountEvent.CreateAccountEvent(
                balance,
                name
            )

        // When
        accountMessageListener.handle(receivedCreationMessage)

        // Then
        verify(commandStore, times(1)).saveCreationCommand(any(CreateAccountCommand::class.java))
        verify(commandBus, times(1)).send(any(CreateAccountCommand::class.java))
    }

    @Test
    fun testHandleUpdateAccountMessage() {
        // Given
        val accountId = "id"
        val balance = BigDecimal.TEN
        val name = "NameName"
        val receivedUpdateMessage =
            AccountEvent.UpdateAccountEvent(
                accountId,
                balance,
                name
            )
        val updateCommandId = IDProvider.nextId
        val expectedUpdateCommand = UpdateAccountCommand(balance, name, accountId, updateCommandId)

        // When
        accountMessageListener.handle(receivedUpdateMessage)

        // Then
        verify(commandStore, times(1)).save(expectedUpdateCommand)
        verify(commandBus, times(1)).send(expectedUpdateCommand)
    }
}