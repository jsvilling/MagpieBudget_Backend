package ch.jvi.magpie.domain.account

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.math.BigDecimal.ZERO

class AccountCommandTest {

    @Test
    fun testCreationCommand_DefaultValues() {
        // Given
        val createAccountCommand = AccountCommand.CreateAccountCommand()

        // When 
        val account = Account(createAccountCommand)

        // Then
        assertThat(account.balance).isEqualTo(ZERO)
    }
}