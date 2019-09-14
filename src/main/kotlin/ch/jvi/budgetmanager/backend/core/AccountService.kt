package ch.jvi.budgetmanager.backend.core

import ch.jvi.budgetmanager.backend.core.message.CreateAccountMessage
import ch.jvi.budgetmanager.core.api.MessageBus
import org.springframework.stereotype.Service
import java.math.BigDecimal


@Service
class AccountService(private val messageBus: MessageBus) {

    fun createAccount(values: Map<String, String>) {
        // TODO Validate & Sanitize parameters
        val balance = BigDecimal(values["balance"].toString())
        val name = values["name"].toString()
        val createAccountMessage = CreateAccountMessage(balance, name)
        messageBus.send(createAccountMessage)
    }
}