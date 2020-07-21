package ch.jvi.budgetmanager.backend.query.account

import ch.jvi.budgetmanager.backend.command.domain.account.Account

object AccountDtoMapper {
    fun mapToAccountDto(account: Account): AccountDto {
        return AccountDto(
            account.id,
            account.name,
            account.balance,
            emptyList()
        )
    }
}