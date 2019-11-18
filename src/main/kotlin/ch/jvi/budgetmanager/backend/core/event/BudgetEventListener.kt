package ch.jvi.budgetmanager.backend.core.event

import ch.jvi.budgetmanager.backend.api.command.CreationCommand
import ch.jvi.budgetmanager.backend.api.command.bus.CommandBus
import ch.jvi.budgetmanager.backend.api.command.store.CommandStore
import ch.jvi.budgetmanager.backend.core.event.BudgetEvent.CreateBudgetEvent
import ch.jvi.budgetmanager.backend.domain.budget.BudgetCommand
import ch.jvi.budgetmanager.backend.domain.budget.BudgetCommand.CreateBudgetCommand
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class BudgetEventListener(private val commandBus: CommandBus, private val commandStore: CommandStore) {

    @EventListener
    fun handle(event: CreateBudgetEvent) {
        val creationCommand = convertToCreationCommand(event)
        commandBus.send(creationCommand)
        commandStore.saveCreationCommand(creationCommand)
    }

    private fun convertToCreationCommand(event: CreateBudgetEvent): CreationCommand {
        return CreateBudgetCommand(event.name, event.target, event.balance)
    }

    @EventListener
    fun handle(event: BudgetEvent.AdjustBudgetBalanceEvent) {
        val command = convertToAdjustBalanceCommand(event)
        commandBus.send(command)
        commandStore.save(command)
    }

    private fun convertToAdjustBalanceCommand(event: BudgetEvent.AdjustBudgetBalanceEvent): BudgetCommand.AdjustBudgetBalanceCommand {
        return BudgetCommand.AdjustBudgetBalanceCommand(event.amount, event.id)
    }

}