package ch.jvi.magpie.service.account

import ch.jvi.magpie.core.api.IDProvider
import ch.jvi.magpie.core.domain.account.AccountEvent
import jdk.nashorn.internal.ir.annotations.Ignore
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import java.math.BigDecimal

internal class AccountEventListenerTest {

    private val accountService = mock(AccountService::class.java)
    private val accountEventListener =
        AccountEventListener(
            accountService
        )

    // Ignored until mockito-kotlin is integrated
    @Test
    @Ignore
    fun testHandleCreateAccountEvent() {
        // Given
        val balance = BigDecimal.TEN
        val name = "NameName"
        val createAccountEvent =
            AccountEvent.CreateAccountEvent(
                balance,
                name
            )

        // When
        // Then
        Assertions.assertThatCode { accountEventListener.handle(createAccountEvent) }.doesNotThrowAnyException()
    }

    @Test
    fun testHandleUpdateAccountEvent() {
        // Given
        val accountId = "id"
        val balance = BigDecimal.TEN
        val name = "NameName"
        val updateAccountEvent =
            AccountEvent.UpdateAccountEvent(
                accountId,
                balance,
                name
            )
        val updateCommandId = IDProvider.nextId

        // When
        // Then
        Assertions.assertThatCode { accountEventListener.handle(updateAccountEvent) }.doesNotThrowAnyException()
    }
}
