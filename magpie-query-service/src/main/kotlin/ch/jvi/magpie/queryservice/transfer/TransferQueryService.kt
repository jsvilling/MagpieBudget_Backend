package ch.jvi.magpie.queryservice.transfer

import ch.jvi.querydomain.transfer.QueryTransfer
import org.springframework.stereotype.Service

@Service
class TransferQueryService(
    private val transferQueryStore: IQueryTransferStore
) {

    fun find(id: String): QueryTransfer {
        return transferQueryStore.findById(id)
    }

    fun findAll(): List<QueryTransfer> {
        return transferQueryStore.findAll()
    }

    fun findAllForAccount(accountId: String): List<QueryTransfer> {
        return transferQueryStore.findAll()
            .filter { it.senderId == accountId || it.recipientId == accountId }
    }

    fun add(transfer: QueryTransfer) {
        transferQueryStore.add(transfer)
    }

    fun update(transfer: QueryTransfer) {
        transferQueryStore.update(transfer, transfer.id)
    }

    fun remove(id: String) {
        transferQueryStore.remove(id)
    }
}