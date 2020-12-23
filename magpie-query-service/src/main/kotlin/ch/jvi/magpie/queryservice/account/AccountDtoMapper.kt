package ch.jvi.magpie.query.account

import ch.jvi.magpie.domain.account.Account

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