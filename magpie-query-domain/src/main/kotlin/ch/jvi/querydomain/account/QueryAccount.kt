package ch.jvi.querydomain.account

import ch.jvi.querydomain.transfer.QueryTransfer
import java.math.BigDecimal

/**
 * @author J. Villing
 */
data class QueryAccount(
    val id: String,
    val name: String,
    val balance: BigDecimal,
    val transfers: MutableList<QueryTransfer>
)
