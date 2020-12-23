package ch.jvi.magpie.queryservice

import java.util.*

/**
 * @author J. Villing
 */
interface QueryModelStore<T, I> {

    fun findAll(): List<T>

    fun findById(id: I): Optional<T>

    fun add(newObj: T): T

    fun update(updatedObj: T, id: I): T

    fun remove(id: I)

}