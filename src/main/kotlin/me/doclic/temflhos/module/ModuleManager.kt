package me.doclic.temflhos.module

object ModuleManager {
    val modules = HashMap<String, Module>()

    fun registerModule(module: Module) {
        if(hasModule(module)) return

        modules.put(module.id, module)
    }
    fun hasModule(module: Module): Boolean = hasModule(module.id)
    fun hasModule(id: String): Boolean = modules.containsKey(id)
    fun getModule(id: String): Module? = modules[id]
}