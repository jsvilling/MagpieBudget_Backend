package ch.jvi.budgetmanager.backend.server

import ch.jvi.budgetmanager.backend.command.api.command.store.CommandStore
import ch.jvi.budgetmanager.backend.command.api.event.EventBus
import ch.jvi.budgetmanager.backend.command.core.service.AccountService
import ch.jvi.budgetmanager.backend.command.domain.account.AccountCommand
import ch.jvi.budgetmanager.backend.query.account.AccountQueryService
import ch.jvi.budgetmanager.backend.server.controller.AccountController
import org.junit.Test
import org.mockito.Mockito.*
import java.math.BigDecimal
import java.math.BigDecimal.TEN

internal class AccountControllerTest {

    val messageBus = mock(EventBus::class.java)

    val commandStore = mock(CommandStore::class.java)

    val accountService = spy(
        AccountService(
            messageBus,
            commandStore
        )
    )

    val accountQueryService = mock(AccountQueryService::class.java)

    val accountController = AccountController(accountService, accountQueryService)

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