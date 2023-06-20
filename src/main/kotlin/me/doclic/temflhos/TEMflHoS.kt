package me.doclic.temflhos

import me.doclic.temflhos.command.ModuleCommand
import me.doclic.temflhos.event.ListenerManager
import me.doclic.temflhos.event.handler.PacketEventHandler
import me.doclic.temflhos.module.HudModule
import me.doclic.temflhos.module.ModuleManager
import me.doclic.temflhos.module.PacketDisablerModule
import net.minecraftforge.client.ClientCommandHandler
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.event.FMLInitializationEvent

@Mod(name = TEMflHoS.NAME, modid = TEMflHoS.MODID, version = TEMflHoS.VERSION, clientSideOnly = true, acceptedMinecraftVersions = "1.8.9")
class TEMflHoS {
    companion object {
        const val NAME = "TEMflHoS"
        const val MODID = "temflhos"
        const val VERSION = "1.0"
    }

    @EventHandler
    fun onInit(e: FMLInitializationEvent) {
        ClientCommandHandler.instance.registerCommand(ModuleCommand)

        ModuleManager.registerModule(PacketDisablerModule())
        ModuleManager.registerModule(HudModule())
        ListenerManager.registerListener(ModuleManager)
        ListenerManager.registerListener(PacketEventHandler)
    }
}