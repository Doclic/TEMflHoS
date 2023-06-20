package me.doclic.temflhos.util

import akka.event.EventBus
import net.minecraft.client.Minecraft
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraftforge.common.MinecraftForge

val mc: Minecraft = Minecraft.getMinecraft()
val localPlayer: EntityPlayerSP
    get() = mc.thePlayer
val eventBus: net.minecraftforge.fml.common.eventhandler.EventBus?
    get() = MinecraftForge.EVENT_BUS