package ch.jvi.budgetmanager.backend.domain

/**
 * This companion object is used to provide unique IDs for Domain Entities.
 *
 * This is only an implementation for local testing. It will be replaced with a proper ID provider assigning UUIDs.
 *
 * @author J. Villing
 */
object IDProvider {

    var idcounter = 0

    fun next(): String {
        return idcounter++.toString()
    }

    fun peekNext(): String {
        return idcounter.toString()
    }
}