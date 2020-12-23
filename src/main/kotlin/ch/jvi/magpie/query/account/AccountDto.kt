package ch.jvi.magpie.query.account

import ch.jvi.magpie.query.transfer.TransferDto
import java.math.BigDecimal

data class AccountDto(
    val id: String,
    val name: String,
    val balance: BigDecimal,
    val transfers: List<TransferDto>
)