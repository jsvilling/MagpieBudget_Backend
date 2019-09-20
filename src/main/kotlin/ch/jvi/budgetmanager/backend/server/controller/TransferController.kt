package ch.jvi.budgetmanager.backend.server.controller

import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
@RequestMapping("/api/transfers")
class TransferController {

    @GetMapping("/{id}")
    fun getTransfer(@PathVariable id: String) = print(id)

    @PutMapping("/create")
    fun createTransfer(@RequestParam senderId: String, @RequestParam recipientId: String, @RequestParam amount: BigDecimal) =
        print(senderId)

    @PostMapping("/{id}/update")
    fun updateTransfer(@PathVariable id: String, @RequestParam senderId: String, @RequestParam recipientId: String, @RequestParam amount: BigDecimal) =
        print(id)
}