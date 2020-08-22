package ch.jvi.budgetmanager.backend.gateway

import ch.jvi.budgetmanager.backend.command.domain.account.service.AccountService
import ch.jvi.budgetmanager.backend.query.account.AccountDto
import ch.jvi.budgetmanager.backend.query.account.AccountQueryService
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

/**
 * Rest Controller for all Account related operations.
 *
 * @author J. Villing
 */
@RestController
@RequestMapping("/api/accounts")
class AccountController(
    private val accountService: AccountService,
    private val accountQueryService: AccountQueryService
) {

    @GetMapping
    fun get(): List<AccountDto> {
        return accountQueryService.findAll()
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: String): AccountDto {
        return accountQueryService.find(id)
    }

    @PostMapping("/create")
    fun create(@RequestParam balance: BigDecimal, @RequestParam name: String) {
        accountService.createAccount(balance, name)
    }

    @PutMapping("/{id}/update")
    fun update(@PathVariable id: String, @RequestParam balance: BigDecimal, @RequestParam name: String) {
        accountService.updateAccount(id, balance, name)
    }
}