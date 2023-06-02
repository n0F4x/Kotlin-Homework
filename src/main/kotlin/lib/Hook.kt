package lib

import kotlin.properties.Delegates
import kotlin.reflect.KProperty

typealias HookListener<T> = (
    property: KProperty<*>, oldValue: T?, newValue: T?
) -> Unit

class Hook<T>(element: T) {
    var element: T by Delegates.observable(element) { property, oldValue, newValue ->
        listeners.forEach { it.invoke(property, oldValue, newValue) }
    }

    private val listeners = mutableListOf<HookListener<T>>()

    fun addListener(listener: HookListener<T>) {
        listeners.add(listener)
    }

    fun removeListener(listener: HookListener<T>) {
        listeners.remove(listener)
    }
}
