package lib

@JvmInline
value class Entity(private val id: ULong) : Comparable<Entity> {
    override fun compareTo(other: Entity): Int {
        return id.compareTo(other.id)
    }
}

class Registry<ID : Comparable<ID>> {
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
    inline fun <reified T> register(): Registry<ID> {
        TODO()
    }

    /**
     * Stores the added element and returns its ID
     */
    fun <T> add(element: T): ID {
        TODO()
    }

    /**
     * Removes an entity
     */
    fun remove(entity: ID) {
        TODO()
    }
}

typealias EntityRegistry = Registry<Entity>

fun <ID : Comparable<ID>> Registry<ID>.get(elementId: ID): Any {
    TODO()
}

fun <ID : Comparable<ID>> Registry<ID>.contains(elementId: ID): Boolean {
    TODO()
}
