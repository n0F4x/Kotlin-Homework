package lib.ecs

import kotlin.reflect.KClass
import kotlin.reflect.cast

/**
 * ECS like entity-component container.
 */
class Registry {
    private val entities = mutableMapOf<KClass<out Any>, MutableList<Pair<Entity, Any>>>()
    private var idCounter: Entity = 0uL

    /**
     * Returns a view to the underlying list.
     */
    fun <T: Any> query(type: KClass<T>): List<T> {
        return (entities[type] ?: return listOf()).map { type.cast(it.second) }
    }

    /**
     * Creates storage for the registered type instances.
     */
    fun <T : Any> registerType(type: KClass<T>) {
        entities.putIfAbsent(type, mutableListOf())
    }

    /**
     * Tries adding an element as entity.
     * Fails if its type was not registered.
     * The returned value is the id of the entity.
     */
    fun <T : Any> add(element: T, type: KClass<T>): Result<Entity> {
        val list = entities[type] ?: return Result.failure(Exception("Type $type was not registered"))
        list.add(Pair(idCounter, element))
        return Result.success(idCounter++)
    }

    /**
     * Removes an entity.
     */
    fun remove(entity: Entity) {
        for (list in entities.values) {
            if (list.removeIf { it.first == entity })
                return
        }
    }

    /**
     * Gets an entity and its class.
     * Prefer using the other methods for better efficiency.
     */
    fun get(entity: Entity): Result<Pair<KClass<out Any>, Any>> {
        if (entity >= idCounter) return Result.failure(Exception("Entity($entity) is not contained"))
        for ((type, list) in entities) {
            return Result.success(type to (list.find { it.first == entity } ?: continue).second)
        }
        return Result.failure(Exception("Entity($entity) is not contained"))
    }

    fun registeredTypes(): List<KClass<out Any>> {
        return entities.keys.toList()
    }
}

//************************//
// Other helper functions //
//************************//

inline fun <reified T: Any> Registry.query(): List<T> {
    return query(T::class)
}

inline fun <reified T : Any> Registry.registerType() {
    registerType(T::class)
}

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
    return registeredTypes().contains(T::class)
}
