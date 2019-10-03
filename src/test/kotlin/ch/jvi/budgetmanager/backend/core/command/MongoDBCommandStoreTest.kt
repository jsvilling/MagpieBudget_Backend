package ch.jvi.budgetmanager.backend.core.command

import ch.jvi.budgetmanager.backend.api.command.Command
import ch.jvi.budgetmanager.backend.api.command.CreationCommand
import ch.jvi.budgetmanager.backend.domain.account.AccountCommand
import ch.jvi.budgetmanager.backend.domain.transfer.TransferCommand
import ch.jvi.budgetmanager.backend.server.repository.CreationCommandRepository
import ch.jvi.budgetmanager.backend.server.repository.UpdateCommandRepository
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import java.math.BigDecimal

internal class MongoDBCommandStoreTest {

    private val creationCommandRepository = Mockito.mock(CreationCommandRepository::class.java)
    private val updateCommandRepository = Mockito.mock(UpdateCommandRepository::class.java)
    private val mongoDBCommandStore = MongoDBCommandStore(creationCommandRepository, updateCommandRepository)

    @Test
    fun find(id: String) {
        // Given
        val entityId = "123"

        // When
        mongoDBCommandStore.find(entityId)

        // Then
        verify(creationCommandRepository, times(1)).findAll()
    }

    @Test
    fun findAccountCommands(id: String) {
        // Given
        val entityId = "123"

        // When
        mongoDBCommandStore.find(entityId)

        // Then
        verify(creationCommandRepository, times(1)).findAll()
    }

    @Test
    fun findTransferCommands(id: String) {
        // Given
        val entityId = "123"

        // When
        mongoDBCommandStore.find(entityId)

        // Then
        verify(updateCommandRepository, times(1)).findAll()
    }

    @Test
    fun findCreationCommand(id: String) {
        // Given
        val entityId = "123"
        val creationCommand = AccountCommand.CreateAccountCommand()
        Mockito.doReturn(listOf(creationCommand)).`when`(creationCommandRepository.findAll())

        // When
        val actualCommand = mongoDBCommandStore.findCreationCommand(entityId)

        // Then
        verify(creationCommandRepository, times(1)).findAll()
        assertThat(actualCommand).isEqualTo(creationCommand)
    }

    @Test
    fun save(command: Command) {
        // Given
        val entityId = "123"
        val updateCommand = AccountCommand.UpdateAccountCommand(BigDecimal.ONE, "Name", entityId)

        // When
        mongoDBCommandStore.save(updateCommand)

        // Then
        verify(updateCommandRepository, times(1)).save(updateCommand)
    }

    @Test
    fun saveCreationCommand(command: CreationCommand) {
        // Given
        val creationCommand = AccountCommand.CreateAccountCommand()

        // When
        mongoDBCommandStore.saveCreationCommand(creationCommand)

        // Then
        verify(creationCommandRepository, times(1)).save(creationCommand)
    }
}
