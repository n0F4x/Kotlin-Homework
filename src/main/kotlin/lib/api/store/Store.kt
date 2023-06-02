package lib.api.store

import lib.api.ecs.Entity
import lib.api.ecs.Registry
import lib.api.ecs.add
import lib.api.ecs.registerType

class Store {
    private val states = Registry()
    private val tagMap = mutableMapOf<String, Entity>()

    fun <T> add(hook: Observed<T>, tag: String): Boolean {
        states.registerType<Observed<T>>()
        tagMap[tag] = states.add(hook) ?: return false
        return true
    }

    fun get(tag: String): Observed<*>? {
        return (states.get(tagMap[tag] ?: return null)?: return null).second as Observed<*>
    }
}
