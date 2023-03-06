package me.doclic.temflhos

import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.event.FMLInitializationEvent

@Mod(modid = TEMflHoS.MODID, version = TEMflHoS.VERSION)
class TEMflHoS {
    companion object {
        const val MODID = "temflhos"
        const val VERSION = "1.0"
    }

    @EventHandler
    fun onInit(e: FMLInitializationEvent) {
        println("epic")
    }
}