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
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.network.FMLNetworkEvent

object PacketEventHandler : Listener {
    @SubscribeEvent
    @Suppress("UNCHECKED_CAST")
    fun onClientConnected(e: FMLNetworkEvent.ClientConnectedToServerEvent) {
        e.manager.channel().pipeline().addBefore(
            "packet_handler",
            TEMflHoS.MODID,
            object : ChannelDuplexHandler() {
                override fun write(ctx: ChannelHandlerContext?, msg: Any?, promise: ChannelPromise?) {
                    val packetEvent = C2SPacketEvent(msg as C2SPacket)
                    ListenerManager.registry.forEach { listener -> listener.onC2SPacket(packetEvent) }

                    if(!packetEvent.cancelled) super.write(ctx, packetEvent.packet, promise)
                }

                override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {
                    val packetEvent = S2CPacketEvent(msg as S2CPacket)
                    ListenerManager.registry.forEach { listener -> listener.onS2CPacket(packetEvent) }

                    if(!packetEvent.cancelled) super.channelRead(ctx, packetEvent.packet)
                }
            })
    }

    @SubscribeEvent
    fun onClientDisconnected(e: FMLNetworkEvent.ClientDisconnectionFromServerEvent) {
        e.manager.channel().pipeline().remove(TEMflHoS.MODID)
    }
}