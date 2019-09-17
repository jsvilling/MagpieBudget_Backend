package ch.jvi.budgetmanager.backend.server

import ch.jvi.budgetmanager.backend.api.command.CommandStore
import ch.jvi.budgetmanager.backend.api.message.MessageBus
import ch.jvi.budgetmanager.backend.core.AccountService
import org.junit.Test
import org.mockito.Mockito.*
import java.math.BigDecimal

internal class AccountControllerTest {

    val messageBus = mock(MessageBus::class.java)
    val commandStore = mock(CommandStore::class.java)
    val accountService = spy(AccountService(messageBus, commandStore))
    val accountController = AccountController(accountService)

    @Test
    fun testGetAccount() {
        // Given
        val id = "someId"

        // When
        accountController.getAccount(id)

        // Then
        verify(accountService, times(1)).getAccount(id)
    }

    @Test
    fun testCreateAccount() {
        // Given
        val balance = BigDecimal.ONE
        val name = "Name"

        // When
        accountController.createAccount(balance, name)

        // Then
        verify(accountService, times(1)).createAccount(balance, name)
    }

    @Test
    fun testUpdateAccount() {
        // Given
        val id = "id"
        val balance = BigDecimal.TEN
        val name = "Name"

        // When
        accountController.updateAccount(id, balance, name)

        // Then
        verify(accountService, times(1)).updateAccount(id, balance, name)
    }
}