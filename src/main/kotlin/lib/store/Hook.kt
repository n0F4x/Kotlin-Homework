package lib.store

import kotlin.reflect.KProperty

/**
 * This class acts as a hook for [Observed] state.
 * Its value over a regular function is that it can be changed dynamically.
 */
data class Hook<T>(var action: Observer<T>) : Observer<T> {
    override operator fun invoke(property: KProperty<*>, oldValue: T?, newValue: T?) {
        action(property, oldValue, newValue)
    }
}
