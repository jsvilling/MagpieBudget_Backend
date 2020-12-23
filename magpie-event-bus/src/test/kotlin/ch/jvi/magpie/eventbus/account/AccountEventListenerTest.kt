package ch.jvi.magpie.commandservice.account

import ch.jvi.magpie.domain.IDProvider
import ch.jvi.magpie.domain.account.AccountEvent
import ch.jvi.magpie.eventbus.account.AccountEventListener
import ch.jvi.magpie.queryservice.account.AccountQueryService
import org.assertj.core.api.Assertions
import org.junit.Ignore
import org.junit.Test
import org.mockito.Mockito.mock
import java.math.BigDecimal

internal class AccountEventListenerTest {

    private val accountService = mock(AccountService::class.java)
    private val queryAccountService = mock(AccountQueryService::class.java)
    private val accountEventListener =
        AccountEventListener(
            accountService,
            queryAccountService
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