package me.doclic.temflhos.module

import me.doclic.temflhos.event.C2SPacketEvent
import me.doclic.temflhos.util.mc
import me.doclic.temflhos.util.tChat
import net.minecraft.block.material.Material
import net.minecraft.network.play.client.C07PacketPlayerDigging
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement
import net.minecraft.util.EnumChatFormatting
import org.lwjgl.input.Keyboard


object SafeInteractModule : Module("safe_interact", "Safe Interact", keyCode = Keyboard.KEY_I, resetOnDisconnect = false) {
    override fun onC2SPacket(e: C2SPacketEvent) {
        preventDragonEggInteract(e)

    }
    private fun preventDragonEggInteract(e: C2SPacketEvent) {
        if (!listOf(C07PacketPlayerDigging::class, C08PacketPlayerBlockPlacement::class).contains(e.packet::class)) return
        val material: Material = if (e.packet is C07PacketPlayerDigging) {
            mc.thePlayer.entityWorld.getBlockState((e.packet as C07PacketPlayerDigging).position).block.material
        } else
            mc.thePlayer.entityWorld.getBlockState((e.packet as C08PacketPlayerBlockPlacement).position).block.material
        if (material != Material.dragonEgg) return
        tChat("${EnumChatFormatting.GREEN}Safe interact prevented you from teleporting that block")
        e.cancelled = true
    }

//    private fun safePlace(event: PlayerInteractEvent) {
//        if (event.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) return
//        val itemStack = localPlayer.heldItem ?: return
//        if (itemStack.item !is ItemBlock) return
//        val block = (itemStack.item as ItemBlock).block
//        val damage = (itemStack.item as ItemBlock).getDamage(itemStack)
//        if (damage == 0) {
//            if (itemStack.hasDisplayName() && itemStack.displayName.contains("null")) {
//                tChat("${EnumChatFormatting.GREEN}Safe interact prevented you from placing that block")
//                event.isCanceled = true;
//            }
//            return
//        }
//
//        if (block in setOf<Block>(Blocks.snow_layer, Blocks.sapling, Blocks.brown_mushroom_block, Blocks.red_mushroom_block, Blocks.stone_slab)) {
//            if (block == Blocks.stone_slab && damage < 8) return
//            tChat("${EnumChatFormatting.GREEN}Safe interact prevented you from placing that block")
//            event.isCanceled = true;
//        }
//    }


}