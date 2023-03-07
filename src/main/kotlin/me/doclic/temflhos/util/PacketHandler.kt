package me.doclic.temflhos.util

import net.minecraft.network.INetHandler
import net.minecraft.network.Packet

interface PacketHandler {
    fun onC2SPacket(packet: Packet<out INetHandler?>): Packet<out INetHandler?>?
    fun onS2CPacket(packet: Packet<out INetHandler?>): Packet<out INetHandler?>?
}