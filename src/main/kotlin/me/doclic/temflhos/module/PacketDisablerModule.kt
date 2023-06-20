package me.doclic.temflhos.module

import me.doclic.temflhos.event.C2SPacketEvent
import me.doclic.temflhos.util.*
import net.minecraft.client.entity.EntityPlayerSP
import java.util.LinkedList

class PacketDisablerModule : Module("packet_disabler", "Packet Disabler") {
    override val disableOnDisconnect: Boolean = true
    private val queuedC2SPackets = LinkedList<C2SPacket>()


    override fun onEnable() {
        val player = localPlayer
        player.tChat("Stopped packet writing")
        playSound(player)
    }

    override fun onDisable() {
        val player = localPlayer
        player.tChat("Re-enabled packet writing")
        playSound(player)

        for (i in 0 until queuedC2SPackets.size) {
            PacketUtil.sendC2SPacket(queuedC2SPackets.first)
            queuedC2SPackets.removeFirst()
        }
    }

    override fun onC2SPacket(e: C2SPacketEvent) {
        e.cancelled = true
        queuedC2SPackets.add(e.packet)
    }

    private fun playSound(player: EntityPlayerSP) {
        player.playSound("random.click", 1f, 1.1f)
    }
}