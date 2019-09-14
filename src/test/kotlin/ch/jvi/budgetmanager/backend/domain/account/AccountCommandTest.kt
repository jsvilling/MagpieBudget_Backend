package ch.jvi.budgetmanager.backend.domain.account

import org.junit.Test
import org.assertj.core.api.Assertions.assertThat as assertThat
import java.math.BigDecimal.ZERO as ZERO

class AccountCommandTest {

    @Test
    fun testCreationCommand_DefaultValues() {
        // Given
        val createAccountCommand = CreateAccountCommand()
        
        // When 
        val account = Account(createAccountCommand)
        
        // Then
        assertThat(account.balance).isEqualTo(ZERO)
    }
}