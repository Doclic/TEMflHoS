package me.doclic.temflhos.module

import me.doclic.temflhos.config.*
import me.doclic.temflhos.event.C2SPacketEvent
import me.doclic.temflhos.event.S2CPacketEvent
import me.doclic.temflhos.util.tChat
import net.minecraft.util.EnumChatFormatting
import org.lwjgl.input.Keyboard

object PacketLoggerModule : Module("packet_logger", "Packet Logger", keyCode = Keyboard.KEY_L, resetOnDisconnect = false) {
    val enableC2S = ConfigNode("enableC2S", true, BooleanConfigType, config)
    val enableS2C = ConfigNode("enableS2C", true, BooleanConfigType, config)

    override fun onC2SPacket(e: C2SPacketEvent) {
        if (!enableC2S.value) return
        tChat("${EnumChatFormatting.GOLD}[C2S] ${e.packet.javaClass.simpleName}")
    }

    override fun onS2CPacket(e: S2CPacketEvent) {
        if (!enableS2C.value) return
        tChat("${EnumChatFormatting.RED}[S2C] ${e.packet.javaClass.simpleName}")
    }

}