package ch.jvi.budgetmanager.backend.server.controller

import ch.jvi.budgetmanager.backend.core.service.AccountService
import ch.jvi.budgetmanager.backend.domain.account.Account
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

/**
 * Rest Controller for all Account related operations.
 *
 * @author J. Villing
 */
@RestController
@RequestMapping("/api/accounts")
class AccountController(private val accountService: AccountService) {

    @GetMapping("/{id}")
    fun get(@PathVariable id: String): Account {
        return accountService.find(id)
    }

    @PutMapping("/create")
    fun create(@RequestParam balance: BigDecimal, @RequestParam name: String) {
        accountService.createAccount(balance, name)
    }

    @PostMapping("/{id}/update")
    fun update(@PathVariable id: String, @RequestParam balance: BigDecimal, @RequestParam name: String) {
        accountService.updateAccount(id, balance, name)
    }
}