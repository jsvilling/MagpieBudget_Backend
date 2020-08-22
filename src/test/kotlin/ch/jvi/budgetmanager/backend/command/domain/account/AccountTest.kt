package ch.jvi.budgetmanager.backend.command.domain.account

import ch.jvi.budgetmanager.backend.command.domain.account.command.AccountCommand.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.math.BigDecimal
import java.math.BigDecimal.TEN
import java.math.BigDecimal.valueOf

class AccountTest {

    @Test
    fun testCreateAccount() {
        // Given
        val balance = valueOf(214.15)
        val name = "AccountName"
        val createAccountCommand = CreateAccountCommand(balance, name)

        // When
        val account = Account(createAccountCommand)

        // Then
        assertThat(account.balance).isEqualTo(balance)
        assertThat(account.name).isEqualTo(name)
    }

    @Test
    fun testUpdateAccount() {
        // Given
        val balance = valueOf(214.15)
        val name = "AccountName"
        val newName = "NewName"
        val newBalance = TEN
        val createAccountCommand = CreateAccountCommand(balance, name)
        val account = Account(createAccountCommand)

        // When
        val updateAccountCommand = UpdateAccountCommand(newBalance, newName, createAccountCommand.entityId);
        account.apply(updateAccountCommand)

        // Then
        assertThat(account.balance).isEqualTo(newBalance)
        assertThat(account.name).isEqualTo(newName)
    }

    @Test
    fun testAdjustAccountBalance() {
        // Given
        val senderBalance = valueOf(214.15)
        val senderName = "AccountName"
        val createSendeAccountCommand = CreateAccountCommand(senderBalance, senderName)
        val recipientBalance = TEN
        val recipientName = "Recipient"
        val createRecipientCommand = CreateAccountCommand(recipientBalance, recipientName)
        val sender = Account(createSendeAccountCommand)
        val recipient = Account(createRecipientCommand)
        val balanceChange = BigDecimal.ONE

        // When
        val adjustRecipientCommand = AdjustAccountBalanceCommand(balanceChange, recipient.id)
        val adjustSenderCommand = AdjustAccountBalanceCommand(balanceChange.negate(), sender.id)
        recipient.apply(adjustRecipientCommand)
        sender.apply(adjustSenderCommand)

        // Then
        assertThat(recipient.balance).isEqualTo(recipientBalance.add(balanceChange))
        assertThat(sender.balance).isEqualTo(senderBalance.subtract(balanceChange))
    }
}