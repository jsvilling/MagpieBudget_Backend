package ch.jvi.magpie.web.gateway

import ch.jvi.magpie.domain.account.IAccountService
import ch.jvi.magpie.query.account.AccountQueryService
import ch.jvi.querydomain.account.QueryAccount
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
    private val accountService: IAccountService,
    private val accountQueryService: AccountQueryService
) {

    @GetMapping
    fun get(): List<QueryAccount> {
        return accountQueryService.findAll()
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: String): QueryAccount {
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