package examples

import lib.store.Hook
import lib.store.Observed
import lib.store.Store

fun main() {
    val state = Observed("Observed hello")
    val listener = Hook<String> { _, _, _ -> println("Hello observed!") }
    state.addListener(listener)
    state.addListener { _, _, _ -> println("Hello observed from lambda!") }
    state.element = "Changed observed hello"

    val store = Store()
    store.add(state, "helloTag")
    store.get("helloTag").onSuccess {
        println(it.element.apply {
            it as Observed<String>
            listener.action = { _, _, _ -> println("Don't remove me \uD83D\uDE2D") }
            it.element = "Removing listener"
            it.removeListener(listener)
            it.element = "No listener"
        })
    }
    store.remove("helloTag")
}
