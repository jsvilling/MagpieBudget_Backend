package ch.jvi.budgetmanager.backend.server.controller

import ch.jvi.budgetmanager.backend.core.service.TransactionService
import ch.jvi.budgetmanager.backend.domain.transaction.Transaction
import ch.jvi.budgetmanager.backend.domain.transaction.TransactionType
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
@RequestMapping("/api/transactions")
class TransactionController(
    private val transactionService: TransactionService
) {

    @GetMapping("{id}")
    fun get(@PathVariable id: String): Transaction {
        return transactionService.find(id)
    }

    @GetMapping
    fun getAll(): List<Transaction> {
        return transactionService.findAll()
    }

    @PostMapping
    fun create(@RequestParam name: String, @RequestParam amount: BigDecimal, @RequestParam type: TransactionType) {
        transactionService.createTransaction(name, amount, type)
    }
}