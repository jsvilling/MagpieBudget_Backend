package ch.jvi.magpie.querystore.account

import ch.jvi.magpie.queryservice.account.IQueryAccountStore
import ch.jvi.querydomain.account.QueryAccount
import org.springframework.stereotype.Service

/**
 * @author J. Villing
 */
@Service
class QueryAccountStore : IQueryAccountStore {

    private val cache = mutableMapOf<String, QueryAccount>()

    override fun findAll(): List<QueryAccount> {
        return cache.values.toList()
    }

    override fun findById(id: String): QueryAccount {
        return cache[id] ?: throw IllegalArgumentException()
    }

    override fun add(account: QueryAccount): QueryAccount {
        cache[account.id] = account
        return account
    }

    override fun update(account: QueryAccount, id: String): QueryAccount {
        cache[id] = account
        return account
    }

    override fun remove(id: String) {
        cache.remove(id)
    }
}