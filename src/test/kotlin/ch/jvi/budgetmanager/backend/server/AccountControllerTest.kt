package ch.jvi.budgetmanager.backend.server

import ch.jvi.budgetmanager.backend.command.domain.account.command.AccountCommand
import ch.jvi.budgetmanager.backend.command.domain.account.persistance.store.AccountCommandStore
import ch.jvi.budgetmanager.backend.command.domain.account.service.AccountService
import ch.jvi.budgetmanager.backend.event.api.EventBus
import ch.jvi.budgetmanager.backend.query.account.AccountQueryService
import ch.jvi.budgetmanager.backend.server.controller.AccountController
import org.junit.Test
import org.mockito.Mockito.*
import java.math.BigDecimal
import java.math.BigDecimal.TEN

internal class AccountControllerTest {

    val eventBus = mock(EventBus::class.java)

    val commandStore = mock(AccountCommandStore::class.java)

    val accountService = spy(
        AccountService(
            commandStore,
            eventBus
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
        verify(accountQueryService, times(1)).find(id)
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