package me.doclic.temflhos.util

import net.minecraft.client.Minecraft
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.eventhandler.EventBus

val mc: Minecraft
    get() = Minecraft.getMinecraft()
val localPlayer: EntityPlayerSP
    get() = mc.thePlayer
val eventBus: EventBus
    get() = MinecraftForge.EVENT_BUS