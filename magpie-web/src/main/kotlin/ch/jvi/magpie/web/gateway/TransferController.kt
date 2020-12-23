package ch.jvi.magpie.web.gateway

import ch.jvi.magpie.domain.command.domain.transfer.event.TransferEvent.UpdateTransferEvent
import ch.jvi.magpie.domain.command.domain.transfer.service.TransferService
import ch.jvi.magpie.query.transfer.TransferDto
import ch.jvi.magpie.query.transfer.TransferQueryService
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

/**
 * Rest Controller for all Transfer related operations.
 *
 * @author J. Villing
 */
@RestController
@RequestMapping("/api/transfers")
class TransferController(
    private val transferService: TransferService,
    private val transferQueryService: TransferQueryService
) {

    @GetMapping()
    fun get(): List<TransferDto> {
        return transferQueryService.findAll()
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: String): TransferDto {
        return transferQueryService.find(id)
    }

    @GetMapping("/forAccount/{id}")
    fun getForAccount(@PathVariable id: String): List<TransferDto> {
        return transferQueryService.findAllForAccount(id);
    }

    @PostMapping("/create")
    fun create(
        @RequestParam senderId: String,
        @RequestParam name: String,
        @RequestParam recipientId: String,
        @RequestParam amount: BigDecimal
    ) {
        transferService.createTransfer(senderId, name, recipientId, amount)
    }

    @PutMapping("/{id}/update")
    fun update(@RequestBody updateTransferEvent: UpdateTransferEvent) {
        transferService.updateTransfer(updateTransferEvent)
    }
}