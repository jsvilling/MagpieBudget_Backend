package ch.jvi.magpie.infra.rest.account

import ch.jvi.magpie.core.domain.account.Account
import ch.jvi.magpie.core.domain.account.IAccountService
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
    private val accountService: IAccountService
) {

    @GetMapping
    fun get(): List<Account> {
        return accountService.findAll()
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: String): Account {
        return accountService.find(id)
    }

    @PostMapping
    fun create(@RequestParam balance: BigDecimal, @RequestParam name: String) {
        accountService.create(balance, name)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: String, @RequestParam balance: BigDecimal, @RequestParam name: String) {
        accountService.update(id, balance, name)
    }
}
