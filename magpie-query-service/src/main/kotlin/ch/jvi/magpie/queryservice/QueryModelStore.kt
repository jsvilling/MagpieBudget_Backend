package ch.jvi.magpie.queryservice

/**
 * @author J. Villing
 */
interface QueryModelStore<T, I> {

    fun findAll(): List<T>

    fun findById(id: I): T

    fun add(newObj: T): T

    fun update(updatedObj: T, id: I): T

    fun remove(id: I)

}