package ch.jvi.budgetmanager.backend.command.api.command.store

interface EntityCommandStore<C, U> {

    fun findCreationCommand(entity: String): C

    fun findAllCreationCommands(): List<C>

    fun findUpdateCommands(entityId: String): List<U>

    fun save(command: U): U

    fun saveAll(commands: List<U>) = commands.forEach { this.save(it) }
}