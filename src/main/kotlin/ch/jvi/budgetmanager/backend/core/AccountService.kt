package ch.jvi.budgetmanager.backend.core

import org.springframework.stereotype.Service


@Service
class AccountService {

    fun createAccount(values: Map<String, String>) {
        print(values)
    }
}