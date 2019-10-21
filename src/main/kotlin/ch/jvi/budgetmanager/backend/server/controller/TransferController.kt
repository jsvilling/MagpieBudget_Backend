package ch.jvi.budgetmanager.backend.server.controller

import ch.jvi.budgetmanager.backend.core.service.TransferService
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

/**
 * Rest Controller for all Transfer related operations.
 *
 * @author J. Villing
 */
@RestController
@RequestMapping("/api/transfers")
class TransferController(private val transferService: TransferService) {

    @GetMapping("/{id}")
    fun getTransfer(@PathVariable id: String) {
        transferService.find(id)
    }

    @PutMapping("/create")
    fun createTransfer(@RequestParam senderId: String, @RequestParam recipientId: String, @RequestParam amount: BigDecimal, @RequestParam budgetId: String) {
        transferService.createTransfer(senderId, recipientId, amount, budgetId)
    }

    @PostMapping("/{id}/update")
    fun updateTransfer(@PathVariable id: String, @RequestParam senderId: String, @RequestParam recipientId: String, @RequestParam amount: BigDecimal) {
        transferService.updateTransfer(id, senderId, recipientId, amount)
    }
}