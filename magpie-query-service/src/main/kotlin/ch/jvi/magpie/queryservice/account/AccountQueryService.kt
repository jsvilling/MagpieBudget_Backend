package ch.jvi.magpie.queryservice.account

import ch.jvi.querydomain.account.QueryAccount
import org.springframework.stereotype.Service

@Service
class AccountQueryService(
    private val queryAccountStore: IQueryAccountStore
) {

    fun find(id: String): QueryAccount {
        return queryAccountStore.findById(id)
    }

    fun findAll(): List<QueryAccount> {
        return queryAccountStore.findAll()
    }

    fun findAccountName(id: String): String {
        return find(id).name
    }

    fun add(account: QueryAccount) {
        queryAccountStore.add(account)
    }

    fun update(account: QueryAccount) {
        val oldAccount = find(account.id)
        queryAccountStore.remove(account.id)
        queryAccountStore.add(
            QueryAccount(
                id = account.id,
                balance = account.balance,
                name = account.name,
                transfers = oldAccount.transfers
            )
        )
    }

    fun remove(id: String) {
        queryAccountStore.remove(id)
    }
}