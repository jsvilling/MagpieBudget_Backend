package ch.jvi.budgetmanager.backend.server.controller

import ch.jvi.budgetmanager.backend.core.service.BudgetService
import ch.jvi.budgetmanager.backend.domain.budget.Budget
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

/**
 * Rest Controller for Budget related operations
 */
@RestController
@RequestMapping("/api/budgets")
class BudgetController(private val budgetService: BudgetService) {

    @GetMapping("{id}")
    fun getBudget(@PathVariable id: String): Budget {
        return budgetService.find(id)
    }

    @PostMapping("create")
    fun createBudget(@RequestParam name: String, @RequestParam target: BigDecimal, @RequestParam balance: BigDecimal) {
        budgetService.create(name, target, balance)
    }
}