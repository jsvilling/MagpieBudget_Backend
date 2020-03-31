package ch.jvi.budgetmanager.backend.integration

import ch.jvi.budgetmanager.backend.core.service.BudgetService
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import java.math.BigDecimal

@RunWith(SpringRunner::class)
@SpringBootTest
@ActiveProfiles("integrationTest")
internal class BudgetIntegrationTest {

    @Autowired
    lateinit var budgetService: BudgetService

    @Test
    fun testBudgetCreation() {
        // Given
        val budgetName = "someBudget"
        val budgetTarget = BigDecimal("100")
        val budgetBalance = BigDecimal.ZERO

        // When
        budgetService.createBudget(budgetName, budgetTarget, budgetBalance)

        // Then
//        val budget = budgetService.find(IDProvider.peekPrevious())
//        assertThat(budget).satisfies {
//            assertThat(it.name).isEqualTo(budgetName)
//            assertThat(it.target).isEqualTo(budgetName)
//            assertThat(it.balance).isEqualTo(budgetName)
    }
    }

}