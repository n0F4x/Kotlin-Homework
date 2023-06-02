package lib.store

import kotlin.reflect.KProperty

/**
 * Observer for the [Observed] class
 */
typealias Observer<T> = (
    property: KProperty<*>, oldValue: T?, newValue: T?
) -> Unit
