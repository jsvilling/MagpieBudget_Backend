package ch.jvi.magpie.web.gateway

import ch.jvi.magpie.domain.command.domain.account.service.IAccountService
import ch.jvi.magpie.query.account.AccountDto
import ch.jvi.magpie.query.account.AccountQueryService
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