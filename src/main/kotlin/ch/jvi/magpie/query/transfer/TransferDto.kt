package ch.jvi.magpie.query.transfer

import java.math.BigDecimal

data class TransferDto(
    val id: String,
    val name: String,
    val amount: BigDecimal,
    val senderId: String,
    val senderName: String,
    val recipientId: String,
    val recipientName: String
)