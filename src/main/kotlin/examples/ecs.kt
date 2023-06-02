package examples

import lib.ecs.*
import kotlin.reflect.cast

fun main() {
    val registry = Registry()
    registry.registerType(String::class)
    check(registry.containsType<String>())
    registry.add("Added entity").onSuccess { entity ->
        println("ID = $entity")
        registry.query<Int>().forEach { println(it) }
        registry.run(entity) { type, value -> println(type.cast(value)) }
        registry.remove(entity)
    }
}
