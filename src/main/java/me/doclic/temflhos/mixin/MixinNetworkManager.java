package me.doclic.temflhos.mixin;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import me.doclic.temflhos.event.ListenerManager;
import me.doclic.temflhos.event.C2SPacketEvent;
import me.doclic.temflhos.event.S2CPacketEvent;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.INetHandlerPlayServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetworkManager.class)
public class MixinNetworkManager {
    @Shadow private Channel channel;

    /*
    @Inject(method = "dispatchPacket", at = @At("HEAD"), cancellable = true)
    private void dispatchPacket(Packet<?> packet, GenericFutureListener<? extends Future<? super Void>>[] listeners, CallbackInfo ci) {
        if(!channel.isOpen()) return;
        // FIXME is there any cleaner way to do this?
        try {
            final C2SPacketEvent e = new C2SPacketEvent((Packet<INetHandlerPlayServer>) packet, false);
            ListenerManager.INSTANCE.getListeners().forEach(listener -> listener.onC2SPacket(e));
            if (e.getCancelled()) ci.cancel();
            packet = e.getPacket();
        } catch (ClassCastException ignored) { }
    }
    */

    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void sendPacket(Packet<?> packet, CallbackInfo ci) {
        if(!channel.isOpen()) return;
        // FIXME is there any cleaner way to do this?
        try {
            final C2SPacketEvent e = new C2SPacketEvent((Packet<INetHandlerPlayServer>) packet, false);
            ListenerManager.INSTANCE.getListeners().forEach(listener -> listener.onC2SPacket(e));
            if (e.getCancelled()) ci.cancel();
            packet = e.getPacket();
        } catch (ClassCastException ignored) { }
    }

    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;Lio/netty/util/concurrent/GenericFutureListener;[Lio/netty/util/concurrent/GenericFutureListener;)V", at = @At("HEAD"), cancellable = true)
    private void sendPacket(Packet packet, GenericFutureListener<? extends Future<? super Void>> listener, GenericFutureListener<? extends Future<? super Void>>[] listeners, CallbackInfo ci) {
        sendPacket(packet, ci);
    }

    @Inject(method = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void channelRead0(ChannelHandlerContext ctx, Packet<?> packet, CallbackInfo ci) {
        if(!channel.isOpen()) return;
        // FIXME is there any cleaner way to do this?
        try {
            final S2CPacketEvent e = new S2CPacketEvent((Packet<INetHandlerPlayClient>) packet, false);
            ListenerManager.INSTANCE.getListeners().forEach(listener -> listener.onS2CPacket(e));
            if (e.getCancelled()) ci.cancel();
            packet = e.getPacket();
        } catch (ClassCastException ignored) { }
    }
}
