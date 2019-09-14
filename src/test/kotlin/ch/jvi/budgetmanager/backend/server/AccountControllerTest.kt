package ch.jvi.budgetmanager.backend.server

import ch.jvi.budgetmanager.backend.core.AccountService
import org.junit.Test
import org.mockito.Mockito

internal class AccountControllerTest {

    var accountService = Mockito.spy(AccountService())
    var accountController = AccountController(accountService)

    @Test
    fun testCreateAccountCall() {
        // When
        val payload: Map<String, String> = emptyMap()
        accountController.createAccount(payload)

        // Then
        Mockito.verify(accountService, Mockito.times(1)).createAccount(payload)
    }
}