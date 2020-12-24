package ch.jvi.magpie.queryservice.transfer

import ch.jvi.magpie.queryservice.QueryModelStore
import ch.jvi.querydomain.transfer.QueryTransfer

/**
 * @author J. Villing
 */
interface IQueryTransferStore : QueryModelStore<QueryTransfer, String> {

    fun findByAccountId(accountId: String): MutableList<QueryTransfer>

}