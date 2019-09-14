package ch.jvi.budgetmanager.backend.core

import ch.jvi.budgetmanager.backend.api.command.CommandStore
import ch.jvi.budgetmanager.backend.api.message.MessageBus
import ch.jvi.budgetmanager.backend.core.message.CreateAccountMessage
import ch.jvi.budgetmanager.backend.domain.account.Account
import ch.jvi.budgetmanager.backend.domain.account.CreateAccountCommand
import org.junit.Test
import org.mockito.Mockito.*
import java.math.BigDecimal
import org.assertj.core.api.Assertions.assertThat as assertThat


internal class AccountServiceTest {

    private val messageBus = mock(MessageBus::class.java)
    private val commandStore = mock(CommandStore::class.java)
    private val accountService = AccountService(messageBus, commandStore)


    @Test
    fun testGetAccount() {
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