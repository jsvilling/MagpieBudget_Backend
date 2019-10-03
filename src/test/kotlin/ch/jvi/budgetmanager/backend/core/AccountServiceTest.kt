package ch.jvi.budgetmanager.backend.core

import ch.jvi.budgetmanager.backend.api.command.store.CommandStore
import ch.jvi.budgetmanager.backend.api.message.MessageBus
import ch.jvi.budgetmanager.backend.core.message.AccountMessage.CreateAccountMessage
import ch.jvi.budgetmanager.backend.domain.account.Account
import ch.jvi.budgetmanager.backend.domain.account.AccountCommand.CreateAccountCommand
import ch.jvi.budgetmanager.backend.domain.account.AccountCommand.UpdateAccountCommand
import org.junit.Test
import org.mockito.Mockito.*
import java.math.BigDecimal
import org.assertj.core.api.Assertions.assertThat as assertThat


internal class AccountServiceTest {

    private val messageBus = mock(MessageBus::class.java)
    private val commandStore = mock(CommandStore::class.java)
    private val accountService = AccountService(messageBus, commandStore)


    @Test
    fun testGetAccount_OnlyCreationCommand() {
        // Given
        val balance = BigDecimal.TEN
        val name = "name"
        val id = "id"
        val creationCommand = CreateAccountCommand(balance, name, id)
        doReturn(creationCommand).`when`(commandStore).findCreationCommand(id)

        // When
        val account = accountService.getAccount(id)

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
        doReturn(listOf(updateCommand)).`when`(commandStore).find(id)

        // When
        val account = accountService.getAccount(id)

        // Then
        assertThat(account).isEqualToComparingFieldByField(updateCommand)
    }

    @Test
    fun testCreateAccount() {
        // Given
        val balance = BigDecimal.TEN
        val name = "name"
        val creationMessage = CreateAccountMessage(balance, name)

        // When
        accountService.createAccount(balance, name)

        // Then
        verify(messageBus, times(1)).send(creationMessage)
    }
}