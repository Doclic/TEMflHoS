package me.doclic.temflhos.module

import me.doclic.temflhos.TEMflHoS
import me.doclic.temflhos.util.PacketHandler
import me.doclic.temflhos.util.PacketUtil
import me.doclic.temflhos.util.player
import me.doclic.temflhos.util.tChat
import net.minecraft.client.entity.EntityPlayerSP
import net.minecraft.network.INetHandler
import net.minecraft.network.Packet
import java.util.LinkedList

class PacketDisablerModule : Module("packet_disabler", "Packet Disabler") {
    override val disableOnDisconnect: Boolean = true
    val queuedC2SPackets = LinkedList<Packet<out INetHandler?>>()
    val packetHandlerName = "${TEMflHoS.MODID}_packet_disabler"
    val packetHandler = object : PacketHandler {
        override fun onC2SPacket(packet: Packet<out INetHandler?>): Packet<out INetHandler?>? {
            queuedC2SPackets.add(packet)

            return null
        }

        override fun onS2CPacket(packet: Packet<out INetHandler?>): Packet<out INetHandler?>? {
            return packet
        }
    }


    override fun onEnable() {
        val player = player()
        player.tChat("Stopped packet writing")
        playSound(player)

        PacketUtil.addHandler(packetHandlerName, packetHandler)
    }

    override fun onDisable() {
        val player = player()
        player.tChat("Re-enabled packet writing")
        playSound(player)

        PacketUtil.removeHandler(packetHandlerName)
        for (i in 0 until queuedC2SPackets.size) {
            PacketUtil.sendC2SPacket(queuedC2SPackets.first)
            queuedC2SPackets.removeFirst()
        }
    }

    private fun playSound(player: EntityPlayerSP) {
        player.playSound("random.click", 1f, 1.1f)
    }
}