package me.doclic.temflhos.module

import me.doclic.temflhos.config.*
import me.doclic.temflhos.event.C2SPacketEvent
import me.doclic.temflhos.event.S2CPacketEvent
import org.lwjgl.input.Keyboard

object PacketFirewallModule : Module("packet_firewall", "Packet Firewall", keyCode = Keyboard.KEY_F, resetOnDisconnect = true) {
    private val c2sPacketFilterList = ConfigNode("c2s_filter_list", emptyList(), ListConfigType(StringConfigType), config)
    private val c2sBlacklistMode = ConfigNode("c2s_blacklist_mode", true, BooleanConfigType, config)

    private val s2cPacketFilterList = ConfigNode("s2c_filter_list", emptyList(), ListConfigType(StringConfigType), config)
    private val s2cBlacklistMode = ConfigNode("s2c_blacklist_mode", false, BooleanConfigType, config)

    override fun onC2SPacket(e: C2SPacketEvent) {
        val inList = c2sPacketFilterList.value.any {p -> e.packet.javaClass.simpleName.lowercase().contains(p.lowercase())}
        if ((inList and c2sBlacklistMode.value) or (!inList and !c2sBlacklistMode.value)) return
        e.cancelled = true
    }

    override fun onS2CPacket(e: S2CPacketEvent) {
        val inList = s2cPacketFilterList.value.any {p -> e.packet.javaClass.simpleName.lowercase().contains(p.lowercase())}
        if ((inList and s2cBlacklistMode.value) or (!inList and !s2cBlacklistMode.value)) return
        e.cancelled = true
    }

}