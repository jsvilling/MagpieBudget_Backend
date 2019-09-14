package ch.jvi.budgetmanager.backend.server

import ch.jvi.budgetmanager.backend.core.AccountService
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
@RequestMapping("/api/accounts")
class AccountController(private val accountService: AccountService) {

    @PutMapping("/create")
    fun createAccount(@RequestParam balance: BigDecimal, @RequestParam name: String)
            = accountService.createAccount(balance, name)

}