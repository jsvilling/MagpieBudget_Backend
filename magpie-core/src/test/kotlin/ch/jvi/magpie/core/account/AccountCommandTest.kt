package ch.jvi.magpie.core.account

import ch.jvi.magpie.core.domain.account.Account
import ch.jvi.magpie.core.domain.account.AccountCommand
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
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
