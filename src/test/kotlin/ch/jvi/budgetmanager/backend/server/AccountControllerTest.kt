package ch.jvi.budgetmanager.backend.server

import org.junit.Test

internal class AccountControllerTest {

    val accountController = AccountController()

    @Test
    fun testCreateAccountCall() {
        // When
        accountController.createAccount(emptyMap())

        // Then
        // No Exception should be thrown
    }
}