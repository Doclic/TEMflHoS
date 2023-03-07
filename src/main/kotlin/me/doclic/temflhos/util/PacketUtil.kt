package me.doclic.temflhos.util

import io.netty.channel.ChannelDuplexHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelPipeline
import io.netty.channel.ChannelPromise
import net.minecraft.network.INetHandler
import net.minecraft.network.Packet

object PacketUtil {
    private fun pipeline(): ChannelPipeline { return mc().netHandler.networkManager.channel().pipeline() }

    fun addHandler(name: String, packetHandler: PacketHandler) {
        if(hasHandler(name)) return

        pipeline().addBefore("packet_handler", name, object : ChannelDuplexHandler() {
            override fun write(ctx: ChannelHandlerContext?, msg: Any?, promise: ChannelPromise?) {
                val packet = packetHandler.onC2SPacket(msg as Packet<out INetHandler?>)

                if(packet != null) super.write(ctx, packet, promise)
            }

            override fun channelRead(ctx: ChannelHandlerContext?, msg: Any?) {
                val packet = packetHandler.onS2CPacket(msg as Packet<out INetHandler?>)

                if(packet != null) super.channelRead(ctx, packet)
            }
        })
    }
    fun hasHandler(name: String): Boolean { return pipeline().get(name) != null }
    fun removeHandler(name: String) { if(hasHandler(name)) pipeline().remove(name) }

    // todo s2c
    fun queueC2SPacket(packet: Packet<out INetHandler?>) { mc().netHandler.addToSendQueue(packet) }
    fun sendC2SPacket(packet: Packet<out INetHandler?>) { mc().netHandler.networkManager.sendPacket(packet) }
}