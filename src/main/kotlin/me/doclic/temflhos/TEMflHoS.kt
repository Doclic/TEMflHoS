package me.doclic.temflhos

import me.doclic.temflhos.command.*
import me.doclic.temflhos.config.ConfigIO
import me.doclic.temflhos.event.ListenerManager
import me.doclic.temflhos.event.handler.PacketEventHandler
import me.doclic.temflhos.module.*
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
        ClientCommandHandler.instance.registerCommand(ConfigCommand)

        ModuleManager.register(PacketDisablerModule)
        ModuleManager.register(PacketFirewallModule)
        ModuleManager.register(HudModule)
        ModuleManager.register(MainMenuModule)
        ModuleManager.register(GhostItemModule)
        ModuleManager.register(SaveGUIModule)
        ModuleManager.register(CrashPatchModule)
        ModuleManager.register(SafeInteractModule)
        ModuleManager.register(PacketLoggerModule)

        ListenerManager.register(ModuleManager)
        ListenerManager.register(PacketEventHandler)

        ConfigIO.reloadConfig()
    }
}