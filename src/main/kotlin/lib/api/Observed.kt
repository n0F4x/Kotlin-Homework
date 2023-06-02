package lib.api

import kotlin.properties.Delegates
import kotlin.reflect.KProperty

typealias Observer<T> = (
    property: KProperty<*>, oldValue: T?, newValue: T?
) -> Unit

class Observed<T>(element: T) {
    var element: T by Delegates.observable(element) { property, oldValue, newValue ->
        listeners.forEach { it.invoke(property, oldValue, newValue) }
    }

    private val listeners = mutableListOf<Observer<T>>()

    fun addListener(listener: Observer<T>) {
        listeners.add(listener)
    }

    fun removeListener(listener: Observer<T>) {
        listeners.remove(listener)
    }
}
