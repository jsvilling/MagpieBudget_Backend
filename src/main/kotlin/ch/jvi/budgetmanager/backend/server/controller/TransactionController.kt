package ch.jvi.budgetmanager.backend.server.controller

import ch.jvi.budgetmanager.backend.core.event.TransactionEvent.CreateTransactionEvent
import ch.jvi.budgetmanager.backend.core.event.TransactionEvent.UpdateTransactionEvent
import ch.jvi.budgetmanager.backend.core.service.TransactionService
import ch.jvi.budgetmanager.backend.domain.transaction.Transaction
import org.springframework.web.bind.annotation.*

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
    fun create(@RequestBody createTransactionEvent: CreateTransactionEvent) {
        transactionService.createTransaction(createTransactionEvent)
    }

    @PutMapping
    fun create(@RequestBody updateTransactionEvent: UpdateTransactionEvent) {
        transactionService.updateTransaction(updateTransactionEvent)
    }
}