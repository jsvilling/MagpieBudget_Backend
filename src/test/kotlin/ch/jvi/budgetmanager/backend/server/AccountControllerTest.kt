package ch.jvi.budgetmanager.backend.server

import ch.jvi.budgetmanager.backend.core.AccountService
import ch.jvi.budgetmanager.core.api.MessageBus
import org.junit.Test
import org.mockito.Mockito.*
import java.math.BigDecimal
import org.mockito.Mockito as Mockito

internal class AccountControllerTest {

    var messageBus = mock(MessageBus::class.java)
    var accountService = spy(AccountService(messageBus))
    var accountController = AccountController(accountService)

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
        val name = "Name";

        // When
        accountController.createAccount(balance, name)

        // Then
        verify(accountService, times(1)).createAccount(balance, name)
    }
}