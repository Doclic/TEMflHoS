package me.doclic.temflhos.module

import me.doclic.temflhos.event.Listener

object ModuleManager : Listener {
    private val modules = HashMap<String, Module>()

    fun registerModule(module: Module) {
        if(hasModule(module)) return

        modules.put(module.id, module)
    }
    fun hasModule(module: Module): Boolean { return hasModule(module.id) }
    fun hasModule(id: String): Boolean { return modules.containsKey(id) }
    fun getModule(id: String): Module? { return modules[id] }

    override fun onPlayerQuit() {
        for (module in modules.values)
            if(module.disableOnDisconnect)
                module.enabled = false
    }
}