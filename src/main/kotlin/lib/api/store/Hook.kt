package lib.api.store

import kotlin.reflect.KProperty

data class Hook<T>(var action: Observer<T>) : Observer<T> {
    override operator fun invoke(property: KProperty<*>, oldValue: T?, newValue: T?) {
        action(property, oldValue, newValue)
    }
}
