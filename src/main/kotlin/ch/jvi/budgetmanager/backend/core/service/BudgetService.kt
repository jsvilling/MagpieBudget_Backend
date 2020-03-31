package ch.jvi.budgetmanager.backend.core.service

import ch.jvi.budgetmanager.backend.api.command.CreationCommand
import ch.jvi.budgetmanager.backend.api.command.store.CommandStore
import ch.jvi.budgetmanager.backend.api.event.EventBus
import ch.jvi.budgetmanager.backend.api.service.EntityService
import ch.jvi.budgetmanager.backend.core.event.BudgetEvent
import ch.jvi.budgetmanager.backend.domain.budget.Budget
import ch.jvi.budgetmanager.backend.domain.budget.BudgetCommand
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class BudgetService(private val eventBus: EventBus, private val commandStore: CommandStore) : EntityService<Budget> {

    override fun find(entityId: String): Budget {
        val creationCommand = commandStore.findCreationCommand(entityId) as BudgetCommand.CreateBudgetCommand
        return Budget(creationCommand)
    }

    fun createBudget(name: String, target: BigDecimal, balance: BigDecimal) {
        val createBudgetEvent = BudgetEvent.CreateBudgetEvent(name, target, balance)
        eventBus.send(createBudgetEvent)
    }

    override fun findAll(): List<Budget> {
        return commandStore.findCreationCommands(this::isBudgetCommand)
            .map { Budget(it as BudgetCommand.CreateBudgetCommand) }
            .map { applyCommands(it) }
    }

    private fun applyCommands(budget: Budget): Budget {
        val commands: List<BudgetCommand> = commandStore.findBudgetCommands(budget.id)
        budget.applyAll(commands)
        return budget
    }

    fun isBudgetCommand(command: CreationCommand): Boolean {
        return command is BudgetCommand.CreateBudgetCommand
    }
}