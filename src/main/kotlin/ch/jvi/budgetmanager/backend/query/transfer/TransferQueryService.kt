package ch.jvi.budgetmanager.backend.query.transfer

import ch.jvi.budgetmanager.backend.command.core.service.TransferService
import ch.jvi.budgetmanager.backend.query.account.AccountQueryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TransferQueryService(
    private val transferService: TransferService
) {

    @Autowired
    lateinit var accountQueryService: AccountQueryService

    fun find(entityId: String): TransferDto {
        val transfer = transferService.find(entityId)
        return TransferDto(
            transfer.id,
            transfer.name,
            transfer.amount,
            transfer.senderId,
            accountQueryService.findAccountName(transfer.senderId),
            transfer.recipientId,
            accountQueryService.findAccountName(transfer.recipientId)
        )
    }

    fun findAll(): List<TransferDto> {
        return transferService.findAll().map {
            TransferDto(
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

    fun findAllForAccount(accountId: String): List<TransferDto> {
        return transferService.findAllForAccount(accountId).map {
            TransferDto(
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