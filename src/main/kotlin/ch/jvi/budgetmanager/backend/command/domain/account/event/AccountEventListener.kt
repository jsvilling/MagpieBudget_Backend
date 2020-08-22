package ch.jvi.budgetmanager.backend.command.domain.account.event

import ch.jvi.budgetmanager.core.api.EventListener
import org.springframework.stereotype.Component

@Component
class AccountEventListener {

    @EventListener
    fun handle(createAccountEvent: AccountEvent.CreateAccountEvent) {
        // TODO: Use in query model to update query model
    }

    @EventListener
    fun handle(updateAccountEvent: AccountEvent.UpdateAccountEvent) {
        // TODO: Use in query model to update query model
    }
}