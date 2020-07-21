package ch.jvi.budgetmanager.backend.command.core

import ch.jvi.budgetmanager.backend.command.api.command.store.CommandStore
import ch.jvi.budgetmanager.backend.command.api.event.EventBus
import ch.jvi.budgetmanager.backend.command.core.event.AccountEvent.CreateAccountEvent
import ch.jvi.budgetmanager.backend.command.core.service.AccountService
import ch.jvi.budgetmanager.backend.command.domain.account.Account
import ch.jvi.budgetmanager.backend.command.domain.account.AccountCommand.CreateAccountCommand
import ch.jvi.budgetmanager.backend.command.domain.account.AccountCommand.UpdateAccountCommand
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.mockito.Mockito.*
import java.math.BigDecimal

internal class AccountServiceTest {

    private val messageBus = mock(EventBus::class.java)
    private val commandStore = mock(CommandStore::class.java)
    private val accountService =
        AccountService(messageBus, commandStore)

    @Test
    fun testGetAccount_OnlyCreationCommand() {
        // Given
        val balance = BigDecimal.TEN
        val name = "name"
        val id = "id"
        val creationCommand = CreateAccountCommand(balance, name, id)
        doReturn(creationCommand).`when`(commandStore).findCreationCommand(id)

        // When
        val account = accountService.find(id)

        // Then
        verify(commandStore, times(1)).findCreationCommand(id)
        assertThat(account).isEqualToComparingFieldByField(Account(creationCommand))
    }

    @Test
    fun testGetAccount_UpdateAccountApplied() {
        // Given
        val balance = BigDecimal.TEN
        val name = "name"
        val id = "id"
        val newBalace = balance.add(BigDecimal.ONE)
        val newName = "newnewnewname"
        val creationCommand = CreateAccountCommand(balance, name, id)
        val updateCommand = UpdateAccountCommand(newBalace, newName, id)
        doReturn(creationCommand).`when`(commandStore).findCreationCommand(id)
        doReturn(listOf(updateCommand)).`when`(commandStore).findAccountCommands(id)

        // When
        val account = accountService.find(id)

        // Then
        assertThat(account).isEqualToIgnoringGivenFields(updateCommand, "id", "creationCommand")
    }

    @Test
    fun testCreateAccount() {
        // Given
        val balance = BigDecimal.TEN
        val name = "name"
        val creationMessage = CreateAccountEvent(balance, name)

        // When
        accountService.createAccount(balance, name)

        // Then
        verify(messageBus, times(1)).send(creationMessage)
    }
}