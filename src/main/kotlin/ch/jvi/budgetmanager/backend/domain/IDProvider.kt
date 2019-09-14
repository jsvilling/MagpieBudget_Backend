package ch.jvi.budgetmanager.backend.domain

object IDProvider {
    var idcounter = 0;

    fun next(): String {
        return idcounter++.toString()
    }
}