package me.doclic.temflhos.module

import me.doclic.temflhos.event.Listener
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.network.FMLNetworkEvent
import java.util.Collections

object ModuleManager : Listener {
    private val writableRegistry = HashMap<String, Module>()

    fun register(module: Module) {
        if(writableRegistry.containsValue(module)) return

        writableRegistry[module.id] = module
    }
    val registry: Map<String, Module>
        get() = Collections.unmodifiableMap(writableRegistry)

    @SubscribeEvent
    fun onClientDisconnected(e: FMLNetworkEvent.ClientDisconnectionFromServerEvent) {
        for (module in writableRegistry.values)
            if(module.resetOnDisconnect)
                module.enabled.value = module.enabledByDefault
    }
}