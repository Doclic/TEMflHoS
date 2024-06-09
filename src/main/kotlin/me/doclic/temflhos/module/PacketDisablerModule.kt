package me.doclic.temflhos.module

import me.doclic.temflhos.config.*
import me.doclic.temflhos.event.C2SPacketEvent
import me.doclic.temflhos.event.S2CPacketEvent
import me.doclic.temflhos.util.C2SPacket
import me.doclic.temflhos.util.PacketUtil
import me.doclic.temflhos.util.S2CPacket
import org.lwjgl.input.Keyboard
import java.util.LinkedList

object PacketDisablerModule : Module("packet_disabler", "Packet Disabler", keyCode = Keyboard.KEY_H, resetOnDisconnect = true) {
    private val queuedC2SPackets = LinkedList<C2SPacket>()
    private val c2sPacketFilterList = ConfigNode("c2s_filter_list", emptyList(), ListConfigType(StringConfigType), config)
    private val c2sBlacklistMode = ConfigNode("c2s_blacklist_mode", true, BooleanConfigType, config)

    private val queuedS2CPackets = LinkedList<S2CPacket>()
    private val s2cPacketFilterList = ConfigNode("s2c_filter_list", emptyList(), ListConfigType(StringConfigType), config)
    private val s2cBlacklistMode = ConfigNode("s2c_blacklist_mode", false, BooleanConfigType, config)


    override fun onDisable() {
        for (i in 0 until queuedC2SPackets.size) {
            PacketUtil.sendC2SPacket(queuedC2SPackets.first)
            queuedC2SPackets.removeFirst()
        }
        for (i in 0 until queuedS2CPackets.size) {
            PacketUtil.sendS2CPacket(queuedS2CPackets.first)
            queuedS2CPackets.removeFirst()
        }
    }

    override fun onC2SPacket(e: C2SPacketEvent) {
        val inList = c2sPacketFilterList.value.any {p -> e.packet.javaClass.simpleName.lowercase().contains(p.lowercase())}
        if ((inList and c2sBlacklistMode.value) or (!inList and !c2sBlacklistMode.value)) return
        e.cancelled = true
        queuedC2SPackets.add(e.packet)
    }

    override fun onS2CPacket(e: S2CPacketEvent) {
        val inList = s2cPacketFilterList.value.any {p -> e.packet.javaClass.simpleName.lowercase().contains(p.lowercase())}
        if ((inList and s2cBlacklistMode.value) or (!inList and !s2cBlacklistMode.value)) return
        e.cancelled = true
        queuedS2CPackets.add(e.packet)
    }

}