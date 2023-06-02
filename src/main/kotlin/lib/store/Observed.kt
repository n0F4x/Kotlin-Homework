package lib.store

import kotlin.properties.Delegates

/**
 * Class for storing state.
 * It publishes events when the state changes. Add an observer to subscribe to this change.
 */
class Observed<T>(element: T) {
    var element: T by Delegates.observable(element) { property, oldValue, newValue ->
        listeners.forEach { it(property, oldValue, newValue) }
    }

    private val listeners = mutableListOf<Observer<T>>()

    fun addListener(listener: Observer<T>) {
        listeners.add(listener)
    }

    fun removeListener(listener: Observer<T>) {
        listeners.remove(listener)
    }
}
