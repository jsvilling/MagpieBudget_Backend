package ch.jvi.budgetmanager.backend.domain.account

import org.junit.Test
import java.math.BigDecimal
import org.assertj.core.api.Assertions.assertThat as assertThat

class AccountTest {

    @Test
    fun testCreateAccountTest() {
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
}