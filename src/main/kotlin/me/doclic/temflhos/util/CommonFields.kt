package me.doclic.temflhos.util

import net.minecraft.client.Minecraft
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraftforge.common.MinecraftForge

val mc: Minecraft = Minecraft.getMinecraft()
val localPlayer: EntityPlayerSP = mc.thePlayer
val eventBus = MinecraftForge.EVENT_BUS