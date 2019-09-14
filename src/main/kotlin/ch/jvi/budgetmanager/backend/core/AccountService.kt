package ch.jvi.budgetmanager.backend.core

import ch.jvi.budgetmanager.backend.core.message.CreateAccountMessage
import ch.jvi.budgetmanager.core.api.MessageBus
import org.springframework.stereotype.Service
import java.math.BigDecimal


@Service
class AccountService(private val messageBus: MessageBus) {

    fun createAccount(balance: BigDecimal, name: String) {
        val createAccountMessage = CreateAccountMessage(balance, name)
        messageBus.send(createAccountMessage)
    }
}