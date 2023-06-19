package me.doclic.temflhos.event

import net.minecraft.network.NetworkManager

interface Listener {
    fun onPlayerJoin(net: NetworkManager) { } // we pass a NetworkManager since the one from mc() hasn't been set yet
    fun onPlayerQuit() { }

    fun onC2SPacket(e: C2SPacketEvent) { }
    fun onS2CPacket(e: S2CPacketEvent) { }
}
