package ch.jvi.budgetmanager.backend.command.domain.api

/**
 * Interface for entity specific services. A
 *
 * Every entity service has to be associated with exactly one entity type and must provide at least a method which
 * will return the entity with the given entityId.
 *
 * @author J. Villing
 */
interface EntityService<out E> {

    /**
     * @param A valid entityId
     * @return The entity with the given entityId
     */
    fun find(entityId: String): E

    fun findAll(): List<E>
}