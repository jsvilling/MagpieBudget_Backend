package ch.jvi.budgetmanager.backend.query.account

import ch.jvi.budgetmanager.backend.query.transfer.TransferDto
import java.math.BigDecimal

data class AccountDto(
    val id: String,
    val name: String,
    val balance: BigDecimal,
    val transfers: List<TransferDto>
)