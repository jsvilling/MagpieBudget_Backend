package ch.jvi.magpie.commandservice.account

import ch.jvi.magpie.commandservice.EventBus
import ch.jvi.magpie.commandservice.IAccountCommandStore
import ch.jvi.magpie.domain.account.Account
import ch.jvi.magpie.domain.account.AccountCommand
import ch.jvi.magpie.domain.account.AccountEvent
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.mockito.Mockito.*
import java.math.BigDecimal

internal class AccountServiceTest {

    private val eventBus = mock(EventBus::class.java)
    private val commandStore = mock(IAccountCommandStore::class.java)
    private val accountService =
        AccountService(commandStore, eventBus)

    @Test
    fun testGetAccount_OnlyCreationCommand() {
        // Given
        val balance = BigDecimal.TEN
        val name = "name"
        val id = "id"
        val creationCommand = AccountCommand.CreateAccountCommand(balance, name, id)
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
        val creationCommand = AccountCommand.CreateAccountCommand(balance, name, id)
        val updateCommand = AccountCommand.UpdateAccountCommand(newBalace, newName, id)
        doReturn(creationCommand).`when`(commandStore).findCreationCommand(id)
        doReturn(listOf(updateCommand)).`when`(commandStore).findUpdateCommands(id)

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
        val creationEvent = AccountEvent.CreateAccountEvent(balance, name)

        // When
        accountService.createAccount(balance, name)

        // Then
        verify(eventBus, times(1)).send(creationEvent)
    }
}