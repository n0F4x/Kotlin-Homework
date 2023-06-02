package lib.api.ecs

import kotlin.reflect.KClass
import kotlin.reflect.cast

class Registry {
    private val entities = mutableMapOf<KClass<out Any>, MutableList<Pair<Entity, Any>>>()
    private var idCounter: Entity = 0uL

    /**
     * Returns a sequence that can be evaluated to a list
     */
    fun <T: Any> query(type: KClass<T>): List<T> {
        return (entities[type] ?: return listOf()).map { type.cast(it.second) }
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
        for (list in entities.values) {
            if (list.removeIf { it.first == entity })
                return
        }
    }

    fun get(entity: Entity): Pair<KClass<out Any>, Any>? {
        if (entity >= idCounter) return null
        for ((type, list) in entities) {
            return type to (list.find { it.first == entity } ?: continue).second
        }
        return null
    }

    fun types(): List<KClass<out Any>> {
        return entities.keys.toList()
    }
}

inline fun <reified T: Any> Registry.query(): Iterable<T> {
    return query(T::class)
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

fun Registry.contains(entity: Entity): Boolean {
    return get(entity) != null
}
