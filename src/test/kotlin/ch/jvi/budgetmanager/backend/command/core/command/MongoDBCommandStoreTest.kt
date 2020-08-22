package ch.jvi.budgetmanager.backend.command.core.command

import ch.jvi.budgetmanager.backend.command.core.command.store.MongoDBCommandStore
import ch.jvi.budgetmanager.backend.command.domain.account.command.AccountCommand
import ch.jvi.budgetmanager.backend.command.domain.transfer.persistence.repository.TransferCreationCommandRepository
import ch.jvi.budgetmanager.backend.command.domain.transfer.persistence.repository.UpdateTransferCommandRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import java.math.BigDecimal

internal class MongoDBCommandStoreTest {

    private val creationCommandRepository = Mockito.mock(TransferCreationCommandRepository::class.java)
    private val updateCommandRepository = Mockito.mock(UpdateTransferCommandRepository::class.java)
    private val mongoDBCommandStore =
        MongoDBCommandStore(
            creationCommandRepository,
            updateCommandRepository
        )

    @Test
    fun find() {
        // Given
        val entityId = "123"

        // When
        mongoDBCommandStore.find(entityId)

        // Then
        verify(updateCommandRepository, times(1)).findAll()
    }

    @Test
    fun findAccountCommands() {
        // Given
        val entityId = "123"

        // When
        mongoDBCommandStore.find(entityId)

        // Then
        verify(updateCommandRepository, times(1)).findAll()
    }

    @Test
    fun findTransferCommands() {
        // Given
        val entityId = "123"

        // When
        mongoDBCommandStore.find(entityId)

        // Then
        verify(updateCommandRepository, times(1)).findAll()
    }

    @Test
    fun findCreationCommand() {
        // Given
        val creationCommand = AccountCommand.CreateAccountCommand()
        val entityId = creationCommand.entityId
        Mockito.doReturn(listOf(creationCommand)).`when`(creationCommandRepository).findAll()

        // When
        val actualCommand = mongoDBCommandStore.findCreationCommand(entityId)

        // Then
        verify(creationCommandRepository, times(1)).findAll()
        assertThat(actualCommand).isEqualTo(creationCommand)
    }

    @Test
    fun save() {
        // Given
        val entityId = "123"
        val updateCommand = AccountCommand.UpdateAccountCommand(BigDecimal.ONE, "Name", entityId)

        // When
        mongoDBCommandStore.save(updateCommand)

        // Then
        verify(updateCommandRepository, times(1)).save(updateCommand)
    }

    @Test
    fun saveCreationCommand() {
        // Given
        val creationCommand = AccountCommand.CreateAccountCommand()

        // When
        mongoDBCommandStore.saveCreationCommand(creationCommand)

        // Then
        verify(creationCommandRepository, times(1)).save(creationCommand)
    }
}
