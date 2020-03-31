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

    var next = UUID.randomUUID().toString();

    var idcounter = 1500

    fun next(): String {
        val id = next
        next = UUID.randomUUID().toString()
        return id;
    }
}