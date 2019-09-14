package ch.jvi.budgetmanager.backend.server

import ch.jvi.budgetmanager.backend.core.AccountService
import ch.jvi.budgetmanager.core.api.MessageBus
import org.junit.Test
import org.mockito.Mockito
import java.math.BigDecimal

internal class AccountControllerTest {

    var messageBus = Mockito.mock(MessageBus::class.java)
    var accountService = Mockito.spy(AccountService(messageBus))
    var accountController = AccountController(accountService)

    @Test
    fun testCreateAccountCall() {
        // When
        val balance = BigDecimal.ONE
        val name = "Name";
        accountController.createAccount(balance, name)

        // Then
        Mockito.verify(accountService, Mockito.times(1)).createAccount(balance, name)
    }


}