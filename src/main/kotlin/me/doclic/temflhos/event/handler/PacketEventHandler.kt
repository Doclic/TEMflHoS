package me.doclic.temflhos.event.handler

import io.netty.channel.ChannelDuplexHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelPromise
import me.doclic.temflhos.TEMflHoS
import me.doclic.temflhos.event.C2SPacketEvent
import me.doclic.temflhos.event.Listener
import me.doclic.temflhos.event.ListenerManager
import me.doclic.temflhos.event.S2CPacketEvent
import me.doclic.temflhos.util.C2SPacket
import me.doclic.temflhos.util.S2CPacket
import me.doclic.temflhos.util.mc
import net.minecraft.network.NetworkManager

object PacketEventHandler : Listener {
    @Suppress("UNCHECKED_CAST")
    override fun onPlayerJoin(net: NetworkManager) {
        net.channel().pipeline().addBefore(
            "packet_handler",
            TEMflHoS.MODID,
            object : ChannelDuplexHandler() {
                override fun write(ctx: ChannelHandlerContext?, msg: Any?, promise: ChannelPromise?) {
                    val e = C2SPacketEvent(msg as C2SPacket)
                    ListenerManager.listeners.forEach { listener -> listener.onC2SPacket(e) }

                    if(!e.cancelled) super.write(ctx, e.packet, promise)
                }

                override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {
                    val e = S2CPacketEvent(msg as S2CPacket)
                    ListenerManager.listeners.forEach { listener -> listener.onS2CPacket(e) }

                    if(!e.cancelled) super.channelRead(ctx, e.packet)
                }
            })
    }

    override fun onPlayerQuit() {
        mc.netHandler.networkManager.channel().pipeline().remove(TEMflHoS.MODID)
    }
}