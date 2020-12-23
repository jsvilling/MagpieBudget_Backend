package ch.jvi.querydomain.transfer

import java.math.BigDecimal

/**
 * @author J. Villing
 */
data class QueryTransfer(
    val id: String,
    val name: String,
    val amount: BigDecimal,
    val senderId: String,
    val senderName: String,
    val recipientId: String,
    val recipientName: String
)
