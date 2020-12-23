package ch.jvi.magpie.command.domain.account

import ch.jvi.magpie.command.domain.account.command.AccountCommand.CreateAccountCommand
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.math.BigDecimal.ZERO

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