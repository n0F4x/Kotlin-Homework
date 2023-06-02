package lib.api

import kotlin.reflect.KClass

typealias Entity = ULong;

class Registry {
    private val entities = mutableMapOf<KClass<out Any>, MutableList<Pair<Entity, Any>>>()
    private var idCounter: Entity = 0uL

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
    fun <T : Any> registerType(type: KClass<T>) {
        entities.putIfAbsent(type, mutableListOf())
    }

    fun <T : Any> add(element: T, type: KClass<T>): Entity? {
        val list = entities[type] ?: return null
        list.add(Pair(idCounter, element))
        return idCounter++
    }

    /**
     * Removes an entity
     */
    fun remove(entity: Entity) {
        TODO()
    }

    fun <T> get(elementId: Entity): T? {
        if (elementId < idCounter) return null
        TODO()
    }

    fun types(): List<KClass<out Any>> {
        return entities.keys.toList()
    }
}

inline fun <reified T : Any> Registry.registerType() {
    registerType(T::class)
}

/**
 * Stores the added element and returns its Entity
 */
inline fun <reified T : Any> Registry.add(element: T): Entity? {
    return add(element, T::class)
}

fun Registry.contains(elementId: Entity): Boolean {
    TODO()
//    return get(elementId) != null
}
