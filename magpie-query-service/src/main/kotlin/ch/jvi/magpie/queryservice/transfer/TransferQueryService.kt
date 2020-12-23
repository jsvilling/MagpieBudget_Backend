package ch.jvi.magpie.query.transfer

import ch.jvi.magpie.domain.transfer.ITransferService
import ch.jvi.magpie.query.account.AccountQueryService
import ch.jvi.querydomain.transfer.QueryTransfer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TransferQueryService(
    private val transferService: ITransferService
) {

    @Autowired
    lateinit var accountQueryService: AccountQueryService

    fun find(entityId: String): QueryTransfer {
        val transfer = transferService.find(entityId)
        return QueryTransfer(
            transfer.id,
            transfer.name,
            transfer.amount,
            transfer.senderId,
            accountQueryService.findAccountName(transfer.senderId),
            transfer.recipientId,
            accountQueryService.findAccountName(transfer.recipientId)
        )
    }

    fun findAll(): List<QueryTransfer> {
        return transferService.findAll().map {
            QueryTransfer(
                it.id,
                it.name,
                it.amount,
                it.senderId,
                accountQueryService.findAccountName(it.senderId),
                it.recipientId,
                accountQueryService.findAccountName(it.recipientId)
            )
        }
    }

    fun findAllForAccount(accountId: String): List<QueryTransfer> {
        return transferService.findAllForAccount(accountId).map {
            QueryTransfer(
                it.id,
                it.name,
                it.amount,
                it.senderId,
                accountQueryService.findAccountName(it.senderId),
                it.recipientId,
                accountQueryService.findAccountName(it.senderId)
            )
        }
    }
}