package me.doclic.temflhos.util

import net.minecraft.network.Packet
import net.minecraft.network.play.INetHandlerPlayClient
import net.minecraft.network.play.INetHandlerPlayServer

object PacketUtil {
    fun sendS2CPacket(packet: S2CPacket) {
        packet.processPacket(mc.netHandler.networkManager.netHandler as INetHandlerPlayClient?)
    }
    fun queueC2SPacket(packet: C2SPacket) { mc.netHandler.addToSendQueue(packet) }
    fun sendC2SPacket(packet: C2SPacket) { mc.netHandler.networkManager.sendPacket(packet) }
}

typealias GenericPacket = Packet<*>
typealias C2SPacket = Packet<in INetHandlerPlayServer>
typealias S2CPacket = Packet<in INetHandlerPlayClient>
