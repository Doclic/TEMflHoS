package me.doclic.temflhos.event

import me.doclic.temflhos.util.C2SPacket
import me.doclic.temflhos.util.S2CPacket

class C2SPacketEvent(var packet: C2SPacket, var cancelled: Boolean = false)
class S2CPacketEvent(var packet: S2CPacket, var cancelled: Boolean = false)
