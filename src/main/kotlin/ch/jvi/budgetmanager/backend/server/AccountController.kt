package ch.jvi.budgetmanager.backend.server

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/accounts")
class AccountController {

    @PutMapping("/create")
    fun createAccount(@RequestBody payload: Map<String, String>) {}

}