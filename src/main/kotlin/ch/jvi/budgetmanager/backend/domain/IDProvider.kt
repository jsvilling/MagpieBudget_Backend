package ch.jvi.budgetmanager.backend.domain

import java.util.*

/**
 * This companion object is used to provide unique IDs for Domain Entities.
 *
 * This is only an implementation for local testing. It will be replaced with a proper ID provider assigning UUIDs.
 *
 * @author J. Villing
 */
object IDProvider {

    var nextId = UUID.randomUUID().toString();

    fun next(): String {
        val id = nextId
        nextId = UUID.randomUUID().toString()
        return id;
    }
}