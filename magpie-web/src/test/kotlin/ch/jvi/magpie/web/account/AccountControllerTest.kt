package ch.jvi.magpie.web.account

import ch.jvi.magpie.commandservice.EventBus
import ch.jvi.magpie.domain.account.IAccountCommandStore
import ch.jvi.magpie.commandservice.account.AccountService
import ch.jvi.magpie.domain.account.AccountCommand
import ch.jvi.magpie.web.gateway.AccountController
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.math.BigDecimal
import java.math.BigDecimal.TEN

internal class AccountControllerTest {

    val eventBus = mock(EventBus::class.java)

    val commandStore = mock(IAccountCommandStore::class.java)

    val accountService = spy(
        AccountService(
            commandStore,
            eventBus
        )
    )

    val accountController = AccountController(accountService)

    @Test
    fun testGetAccount() {
        // Given
        val id = "someId"
        val creationCommand = AccountCommand.CreateAccountCommand(TEN, "name", id)
        `when`(commandStore.findCreationCommand(id)).thenReturn(creationCommand)

        // When
        accountController.get(id)

        // Then
        verify(accountService, times(1)).find(id)
    }

    @Test
    fun testCreateAccount() {
        // Given
        val balance = BigDecimal.ONE
        val name = "Name"

        // When
        accountController.create(balance, name)

        // Then
        verify(accountService, times(1)).createAccount(balance, name)
    }

    @Test
    fun testUpdateAccount() {
        // Given
        val id = "id"
        val balance = TEN
        val name = "Name"

        // When
        accountController.update(id, balance, name)

        // Then
        verify(accountService, times(1)).updateAccount(id, balance, name)
    }
}
