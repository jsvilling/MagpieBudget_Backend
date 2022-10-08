package ch.jvi.magpie.infra.rest.account

import ch.jvi.magpie.core.domain.account.Account
import ch.jvi.magpie.core.domain.account.AccountCommand
import ch.jvi.magpie.core.domain.account.AccountCommand.CreateAccountCommand
import ch.jvi.magpie.core.domain.account.AccountCommand.UpdateAccountCommand
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
    fun create(createAccountCommand: CreateAccountCommand) {
        accountService.create(createAccountCommand)
    }

    @PutMapping("/{id}")
    fun update(updateAccountCommand: UpdateAccountCommand) {
        accountService.update(updateAccountCommand)
    }
}
