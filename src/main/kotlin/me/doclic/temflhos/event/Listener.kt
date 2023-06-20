package me.doclic.temflhos.event

interface Listener {
    fun onC2SPacket(e: C2SPacketEvent) { }
    fun onS2CPacket(e: S2CPacketEvent) { }
}
