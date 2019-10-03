package ch.jvi.budgetmanager.backend.integration.config

import ch.jvi.budgetmanager.backend.api.command.store.CommandStore
import ch.jvi.budgetmanager.backend.api.message.MessageBus
import ch.jvi.budgetmanager.backend.core.AccountService
import ch.jvi.budgetmanager.backend.core.TransferService
import ch.jvi.budgetmanager.backend.core.command.store.InMemoryCommandStore
import ch.jvi.budgetmanager.backend.core.message.MessageBusBusImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class IntegrationTestConfiguration {

    @Autowired
    lateinit var publisher: ApplicationEventPublisher

    @Bean
    fun accountService(): AccountService {
        return AccountService(messageBus(), commandStore())
    }

    @Bean
    fun transferService(): TransferService {
        return TransferService(commandStore(), messageBus())
    }

    @Bean
    fun messageBus(): MessageBus {
        return MessageBusBusImpl(publisher)
    }

    @Bean
    fun commandStore(): CommandStore {
        return InMemoryCommandStore()
    }


}