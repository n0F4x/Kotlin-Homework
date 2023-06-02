package lib.store

import kotlin.reflect.KProperty

typealias Observer<T> = (
    property: KProperty<*>, oldValue: T?, newValue: T?
) -> Unit
