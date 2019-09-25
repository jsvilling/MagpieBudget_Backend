package ch.jvi.budgetmanager.backend.domain.account

import ch.jvi.budgetmanager.backend.domain.IDProvider
import org.junit.Test
import java.math.BigDecimal
import org.assertj.core.api.Assertions.assertThat as assertThat
import ch.jvi.budgetmanager.backend.domain.account.AccountCommand.*

class AccountTest {

    @Test
    fun testCreateAccount() {
        // Given
        val balance = BigDecimal.valueOf(214.15)
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
        val balance = BigDecimal.valueOf(214.15)
        val name = "AccountName"
        val newName = "NewName"
        val newBalance = BigDecimal.TEN
        val createAccountCommand = CreateAccountCommand(balance, name)
        val account = Account(createAccountCommand)

        // When
        val updateAccountCommand = UpdateAccountCommand(balance, name, IDProvider.idcounter.toString());
        account.apply(updateAccountCommand)

        // Then
        assertThat(account.balance).isEqualTo(newBalance)
        assertThat(account.name).isEqualTo(newName)
    }

    @Test
    fun testAdjustAccountBalance() {
        // Given
        val senderBalance = BigDecimal.valueOf(214.15)
        val senderName = "AccountName"
        val createSendeAccountCommand = CreateAccountCommand(senderBalance, senderName)
        val recipientBalance = BigDecimal.TEN
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