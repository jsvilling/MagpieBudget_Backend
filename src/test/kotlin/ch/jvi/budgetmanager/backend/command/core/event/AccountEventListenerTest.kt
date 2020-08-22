package ch.jvi.budgetmanager.backend.command.core.event

import ch.jvi.budgetmanager.backend.command.api.command.bus.CommandBus
import ch.jvi.budgetmanager.backend.command.api.command.store.CommandStore
import ch.jvi.budgetmanager.backend.command.domain.IDProvider
import ch.jvi.budgetmanager.backend.command.domain.account.command.AccountCommand.CreateAccountCommand
import ch.jvi.budgetmanager.backend.command.domain.account.command.AccountCommand.UpdateAccountCommand
import ch.jvi.budgetmanager.backend.command.domain.account.event.AccountEvent
import ch.jvi.budgetmanager.backend.command.domain.account.event.AccountEventListener
import org.junit.Ignore
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.*
import java.math.BigDecimal

internal class AccountEventListenerTest {

    private val commandStore = mock(CommandStore::class.java)
    private val commandBus = mock(CommandBus::class.java)
    private val accountEventListener =
        AccountEventListener(
            commandStore,
            commandBus
        )
    private val captor = ArgumentCaptor.forClass(UpdateAccountCommand::class.java)

    // Ignored until mockito-kotlin is integrated
    @Test
    @Ignore
    fun testHandleCreateAccountEvent() {
        // Given
        val balance = BigDecimal.TEN
        val name = "NameName"
        val accountId = IDProvider.nextId
        val createAccountEvent =
            AccountEvent.CreateAccountEvent(
                balance,
                name
            )

        // When
        accountEventListener.handle(createAccountEvent)

        // Then
        verify(commandStore, times(1)).saveCreationCommand(any(CreateAccountCommand::class.java))
        verify(commandBus, times(1)).send(any(CreateAccountCommand::class.java))
    }

    @Test
    fun testHandleUpdateAccountEvent() {
        // Given
        val accountId = "id"
        val balance = BigDecimal.TEN
        val name = "NameName"
        val updateAccountEvent =
            AccountEvent.UpdateAccountEvent(
                accountId,
                balance,
                name
            )
        val updateCommandId = IDProvider.nextId
        val expectedUpdateCommand = UpdateAccountCommand(balance, name, accountId, updateCommandId)

        // When
        accountEventListener.handle(updateAccountEvent)

        // Then
        verify(commandStore, times(1)).save(expectedUpdateCommand)
        verify(commandBus, times(1)).send(expectedUpdateCommand)
    }
}