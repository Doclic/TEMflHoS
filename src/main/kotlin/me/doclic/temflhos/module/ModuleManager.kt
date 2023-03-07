package me.doclic.temflhos.module

object ModuleManager {
    private val modules = HashMap<String, Module>()

    fun registerModule(module: Module) {
        if(hasModule(module)) return

        modules.put(module.id, module)
    }
    fun hasModule(module: Module): Boolean { return hasModule(module.id) }
    fun hasModule(id: String): Boolean { return modules.containsKey(id) }
    fun getModule(id: String): Module? { return modules[id] }
}