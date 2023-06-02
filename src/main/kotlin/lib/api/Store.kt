package lib.api

class Store {
    private val hooks = Registry()
    private val tagMap = mutableMapOf<String, Entity>()

    fun <T> addHook(hook: Hook<T>, tag: String): Boolean {
        tagMap[tag] = hooks.add(tag to hooks.add(hook)) ?: return false
        return true
    }

    fun getHook(tag: String): Any? {
        return hooks.get(tagMap[tag] ?: TODO())
    }
}
