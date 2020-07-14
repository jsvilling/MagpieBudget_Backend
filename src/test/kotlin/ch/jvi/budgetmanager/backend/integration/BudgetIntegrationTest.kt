package ch.jvi.budgetmanager.backend.integration

import ch.jvi.budgetmanager.backend.core.service.BudgetService
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
@ActiveProfiles("integrationTest")
internal class BudgetIntegrationTest {

    @Autowired
    lateinit var budgetService: BudgetService

}