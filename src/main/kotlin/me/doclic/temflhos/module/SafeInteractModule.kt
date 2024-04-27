package me.doclic.temflhos.module

import me.doclic.temflhos.util.localPlayer
import me.doclic.temflhos.util.mc
import me.doclic.temflhos.util.tChat
import net.minecraft.block.Block
import net.minecraft.client.Minecraft
import net.minecraft.init.Blocks
import net.minecraft.item.ItemBlock
import net.minecraft.util.EnumChatFormatting
import net.minecraft.util.MovingObjectPosition.MovingObjectType
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.InputEvent
import org.lwjgl.input.Keyboard


object SafeInteractModule : Module("safe_interact", "Safe Interact", keyCode = Keyboard.KEY_I, resetOnDisconnect = false) {
    @SubscribeEvent
    fun interact(event: PlayerInteractEvent) {
        safePlace(event)
        antiEggTp(event)


    }

    private fun safePlace(event: PlayerInteractEvent) {
        if (event.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) return
        val itemStack = localPlayer.heldItem ?: return
        if (itemStack.item !is ItemBlock) return
        val block = (itemStack.item as ItemBlock).block
        val damage = (itemStack.item as ItemBlock).getDamage(itemStack)
        if (damage == 0) {
            if (itemStack.hasDisplayName() && itemStack.displayName.contains("null")) {
                tChat("${EnumChatFormatting.GREEN}Safe interact prevented you from placing that block")
                event.isCanceled = true;
            }
            return
        }

        if (block in setOf<Block>(Blocks.snow_layer, Blocks.sapling, Blocks.brown_mushroom_block, Blocks.red_mushroom_block, Blocks.stone_slab)) {
            if (block == Blocks.stone_slab && damage < 8) return
            tChat("${EnumChatFormatting.GREEN}Safe interact prevented you from placing that block")
            event.isCanceled = true;
        }
    }
    private fun antiEggTp(event: PlayerInteractEvent) {

        if (event.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK && event.action != PlayerInteractEvent.Action.LEFT_CLICK_BLOCK) return
        val clickedBlock = event.world.getBlockState(event.pos).block
        if (clickedBlock != Blocks.dragon_egg) return

    }

}