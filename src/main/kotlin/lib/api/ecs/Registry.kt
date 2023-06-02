package lib.api.ecs

import kotlin.reflect.KClass
import kotlin.reflect.cast

class Registry {
    private val entities = mutableMapOf<KClass<out Any>, MutableList<Pair<Entity, Any>>>()
    private var idCounter: Entity = 0uL

    /**
     * Returns a view to the underlying list
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

    fun <T : Any> add(element: T, type: KClass<T>): Result<Entity> {
        val list = entities[type] ?: return Result.failure(Exception("Type $type was not registered"))
        list.add(Pair(idCounter, element))
        return Result.success(idCounter++)
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

    fun get(entity: Entity): Result<Pair<KClass<out Any>, Any>> {
        if (entity >= idCounter) return Result.failure(Exception("Entity($entity) is not contained"))
        for ((type, list) in entities) {
            return Result.success(type to (list.find { it.first == entity } ?: continue).second)
        }
        return Result.failure(Exception("Entity($entity) is not contained"))
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
inline fun <reified T : Any> Registry.add(element: T): Result<Entity> {
    return add(element, T::class)
}

fun Registry.run(entity: Entity, block: (type: KClass<out Any>, element: Any) -> Unit) {
    get(entity).onSuccess { block(it.first, it.second) }
}

fun Registry.contains(entity: Entity): Boolean {
    return get(entity).isSuccess
}

inline fun <reified T: Any> Registry.containsType(): Boolean {
    return types().contains(T::class)
}
