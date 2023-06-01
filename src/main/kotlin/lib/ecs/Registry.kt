package lib.ecs

class Registry {
    @JvmInline
    value class Entity(val id: ULong)

    /**
     * Returns an immutable list to the stored elements
     */
    inline fun <reified T> viewAll(): List<T> {
        TODO()
    }

    /**
     * Returns a sequence that can be evaluated to a list
     */
    inline fun <reified T> query(): Sequence<T> {
        TODO()
    }

    /**
     * Returns a sequence that can be evaluated to a frozen list
     */
    inline fun <reified T> frozenQuery(): Sequence<T> {
        TODO()
    }

    /**
     * Creates storage for newly registered types
     */
    inline fun <reified T> register(): Registry {
        TODO()
    }

    /**
     * Stores the added element and returns its ID
     */
    fun <T> add(element: T): Entity {
        TODO()
    }

    /**
     * Removes an entity
     */
    fun remove(entity: Entity) {
        TODO()
    }
}
