package me.doclic.temflhos.event

import me.doclic.temflhos.util.C2SPacket

class C2SPacketEvent(var packet: C2SPacket, var cancelled: Boolean = false)