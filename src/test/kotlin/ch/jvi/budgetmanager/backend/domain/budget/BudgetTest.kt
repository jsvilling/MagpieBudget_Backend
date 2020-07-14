package ch.jvi.budgetmanager.backend.domain.budget

import ch.jvi.budgetmanager.backend.domain.IDProvider
import ch.jvi.budgetmanager.backend.domain.budget.BudgetCommand.AdjustBudgetBalanceCommand
import ch.jvi.budgetmanager.backend.domain.budget.BudgetCommand.CreateBudgetCommand
import org.junit.Test
import java.math.BigDecimal
import java.math.BigDecimal.TEN
import org.assertj.core.api.Assertions.assertThat as assertThat1

internal class BudgetTest {

    private val name = "Name"
    private val target = BigDecimal("100.0")
    private val balance = BigDecimal.ZERO
    private val creationCommand = CreateBudgetCommand(name, target, balance)


    @Test
    fun testBudgetCreation() {
        // When
        val budget = Budget(creationCommand)

        // Then
        assertThat1(budget.name).isEqualTo(name)
        assertThat1(budget.target).isEqualTo(target)
        assertThat1(budget.balance).isEqualTo(balance)
    }

    @Test
    fun testApplyBalanceChange() {
        // Given
        val balanceChange = TEN
        val budget = Budget(creationCommand)
        val adjustBudgetBalanceCommand = AdjustBudgetBalanceCommand(balanceChange, IDProvider.next)

        // When
        budget.apply(adjustBudgetBalanceCommand)

        // Then
        assertThat1(budget.name).isEqualTo(name)
        assertThat1(budget.target).isEqualTo(target)
        assertThat1(budget.balance).isEqualTo(balance.add(balanceChange))
    }


    private fun getCreationCommand(): CreateBudgetCommand {
        val name = "Name"
        val target = BigDecimal("100.0")
        val balance = BigDecimal.ZERO
        return CreateBudgetCommand(name, target, balance)
    }

}
