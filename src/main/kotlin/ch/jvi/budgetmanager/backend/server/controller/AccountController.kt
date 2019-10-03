package ch.jvi.budgetmanager.backend.server.controller

import ch.jvi.budgetmanager.backend.core.AccountService
import ch.jvi.budgetmanager.backend.domain.account.Account
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
@RequestMapping("/api/accounts")
class AccountController(private val accountService: AccountService) {

    @GetMapping("/{id}")
    fun getAccount(@PathVariable id: String): Account {
        return accountService.getAccount(id)
    }

    @PutMapping("/create")
    fun createAccount(@RequestParam balance: BigDecimal, @RequestParam name: String) {
        accountService.createAccount(balance, name)
    }

    @PostMapping("/{id}/update")
    fun updateAccount(@PathVariable id: String, @RequestParam balance: BigDecimal, @RequestParam name: String) {
        accountService.updateAccount(id, balance, name)
    }
}