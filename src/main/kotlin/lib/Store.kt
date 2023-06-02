package lib

class Store {
    private val hooks = EntityRegistry()
    private val tagMap = mapOf<String, Entity>()

    fun <T> addHook(hook: Hook<T>, tag: String) {
        hooks.add(tag to hooks.add(hook))
    }

    fun getHook(tag: String): Any {
        return hooks.get(tagMap[tag] ?: TODO())
    }
}
