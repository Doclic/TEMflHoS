package me.doclic.temflhos.module

import me.doclic.temflhos.event.C2SPacketEvent
import me.doclic.temflhos.util.C2SPacket
import me.doclic.temflhos.util.PacketUtil
import org.lwjgl.input.Keyboard
import java.util.LinkedList

object PacketDisablerModule : Module("packet_disabler", "Packet Disabler", keyCode = Keyboard.KEY_H, resetOnDisconnect = true) {
    private val queuedC2SPackets = LinkedList<C2SPacket>()

    override fun onDisable() {
        for (i in 0 until queuedC2SPackets.size) {
            PacketUtil.sendC2SPacket(queuedC2SPackets.first)
            queuedC2SPackets.removeFirst()
        }
    }

    override fun onC2SPacket(e: C2SPacketEvent) {
        e.cancelled = true
        queuedC2SPackets.add(e.packet)
    }
}