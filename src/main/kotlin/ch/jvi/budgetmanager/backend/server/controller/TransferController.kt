package ch.jvi.budgetmanager.backend.server.controller

import ch.jvi.budgetmanager.backend.core.TransferService
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
@RequestMapping("/api/transfers")
class TransferController(private val transferService: TransferService) {

    @GetMapping("/{id}")
    fun getTransfer(@PathVariable id: String) = transferService::getTransfer

    @PutMapping("/create")
    fun createTransfer(@RequestParam senderId: String, @RequestParam recipientId: String, @RequestParam amount: BigDecimal) =
        transferService::createTransfer

    @PostMapping("/{id}/update")
    fun updateTransfer(@PathVariable id: String, @RequestParam senderId: String, @RequestParam recipientId: String, @RequestParam amount: BigDecimal) =
        transferService::updateTransfer
}