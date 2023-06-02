package lib.store

import lib.ecs.Entity
import lib.ecs.Registry
import lib.ecs.add
import lib.ecs.registerType

class Store {
    private val states = Registry()
    private val tagMap = mutableMapOf<String, Entity>()

    fun <T> add(hook: Observed<T>, tag: String): Boolean {
        states.registerType<Observed<T>>()
        tagMap[tag] = states.add(hook).getOrElse { return false }
        return true
    }

    fun remove(tag: String) {
        states.remove(tagMap[tag] ?: return)
        tagMap.remove(tag)
    }

    fun get(tag: String): Result<Observed<*>> {
        return Result.success(
            states.get(tagMap[tag] ?: return Result.failure(Exception("Tag $tag is not contained")))
                .getOrElse { return Result.failure(it) }.second as Observed<*>
        )
    }
}
