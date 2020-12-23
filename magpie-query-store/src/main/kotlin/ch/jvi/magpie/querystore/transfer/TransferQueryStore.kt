package ch.jvi.magpie.querystore.transfer

import ch.jvi.magpie.queryservice.transfer.IQueryTransferStore
import ch.jvi.querydomain.transfer.QueryTransfer
import org.springframework.stereotype.Service

/**
 * @author J. Villing
 */
@Service
class TransferQueryStore : IQueryTransferStore {

    private val cache = mutableMapOf<String, QueryTransfer>()

    override fun findAll(): List<QueryTransfer> {
        return cache.values.toList()
    }

    override fun findById(id: String): QueryTransfer {
        return cache[id] ?: throw IllegalArgumentException()
    }

    override fun add(transfer: QueryTransfer): QueryTransfer {
        cache[transfer.id] = transfer
        return transfer
    }

    override fun update(transfer: QueryTransfer, id: String): QueryTransfer {
        cache[id] = transfer
        return transfer
    }

    override fun remove(id: String) {
        cache.remove(id)
    }
}