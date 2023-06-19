package me.doclic.temflhos.event

import me.doclic.temflhos.util.S2CPacket

class S2CPacketEvent(var packet: S2CPacket, var cancelled: Boolean = false)