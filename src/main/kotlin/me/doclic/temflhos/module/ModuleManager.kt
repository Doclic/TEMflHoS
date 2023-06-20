package me.doclic.temflhos.module

import me.doclic.temflhos.event.Listener
import java.util.Collections

object ModuleManager : Listener {
    private val writableModules = HashMap<String, Module>()

    val modules: Map<String, Module>
        get() = Collections.unmodifiableMap(writableModules)
    fun registerModule(module: Module) {
        if(hasModule(module)) return

        writableModules[module.id] = module
    }
    fun hasModule(module: Module): Boolean = hasModule(module.id)
    fun hasModule(id: String): Boolean = writableModules.containsKey(id)
    fun getModule(id: String): Module? = writableModules[id]

    override fun onPlayerQuit() {
        for (module in writableModules.values)
            if(module.disableOnDisconnect)
                module.enabled.value = false
    }
}