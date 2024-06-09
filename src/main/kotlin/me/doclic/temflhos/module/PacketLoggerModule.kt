package me.doclic.temflhos.module

import me.doclic.temflhos.config.*
import me.doclic.temflhos.event.C2SPacketEvent
import me.doclic.temflhos.event.S2CPacketEvent
import me.doclic.temflhos.util.tChat
import net.minecraft.util.EnumChatFormatting
import org.lwjgl.input.Keyboard

object PacketLoggerModule : Module("packet_logger", "Packet Logger", keyCode = Keyboard.KEY_L, resetOnDisconnect = false) {
    private val enableC2S = ConfigNode("enableC2S", true, BooleanConfigType, config)
    private val enableS2C = ConfigNode("enableS2C", true, BooleanConfigType, config)
    private val c2sPacketFilterList = ConfigNode("c2s_filter_list", emptyList(), ListConfigType(StringConfigType), config)
    private val s2cPacketFilterList = ConfigNode("s2c_filter_list", emptyList(), ListConfigType(StringConfigType), config)


    override fun onC2SPacket(e: C2SPacketEvent) {
        if (!enableC2S.value) return
        if (c2sPacketFilterList.value.any { p -> e.packet.javaClass.simpleName.lowercase().contains(p.lowercase())}) return

        tChat("${EnumChatFormatting.GOLD}[C2S] ${e.packet.javaClass.simpleName}")
    }

    override fun onS2CPacket(e: S2CPacketEvent) {
        if (!enableS2C.value) return
        if (s2cPacketFilterList.value.any { p -> e.packet.javaClass.simpleName.lowercase().contains(p.lowercase())}) return

        tChat("${EnumChatFormatting.RED}[S2C] ${e.packet.javaClass.simpleName}")
    }

}