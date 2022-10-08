package ch.jvi.magpie.queryservice.transfer

import ch.jvi.magpie.domain.transfer.ITransferService
import ch.jvi.magpie.queryservice.account.AccountQueryService
import ch.jvi.magpie.query.transfer.TransferDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@Deprecated("This is just a loose draft for testing out some things, do not use it")
class TransferQueryService(
    private val transferService: ITransferService
) {

    // TODO: Revise - This is just a loose draft for testing out some things

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
