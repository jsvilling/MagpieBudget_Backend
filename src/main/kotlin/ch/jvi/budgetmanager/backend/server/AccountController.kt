package ch.jvi.budgetmanager.backend.server

import ch.jvi.budgetmanager.backend.core.AccountService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/accounts")
class AccountController(private val accountService: AccountService) {

    @PutMapping("/create")
    fun createAccount(@RequestBody payload: Map<String, String>) = accountService.createAccount(payload)

}