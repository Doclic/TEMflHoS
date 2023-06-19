package me.doclic.temflhos.mixin;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import me.doclic.temflhos.event.Listener;
import me.doclic.temflhos.event.ListenerManager;
import net.minecraft.network.NetworkManager;
import net.minecraftforge.fml.common.network.handshake.NetworkDispatcher;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetworkDispatcher.class)
abstract public class MixinNetworkDispatcher {
    @Shadow(remap = false) @Final public NetworkManager manager;

    @Inject(method = "completeClientSideConnection", at = @At("HEAD"), remap = false)
    private void completeClientSideConnection(@Coerce Object type, CallbackInfo ci) {
        ListenerManager.INSTANCE.getListeners().forEach(listener -> listener.onPlayerJoin(manager));
    }

    @Inject(method = "disconnect", at = @At("HEAD"), remap = false)
    private void disconnect(ChannelHandlerContext ctx, ChannelPromise promise, CallbackInfo ci) {
        ListenerManager.INSTANCE.getListeners().forEach(Listener::onPlayerQuit);
    }

    @Inject(method = "close", at = @At("HEAD"), remap = false)
    private void close(ChannelHandlerContext ctx, ChannelPromise promise, CallbackInfo ci) {
        ListenerManager.INSTANCE.getListeners().forEach(Listener::onPlayerQuit);
    }
}
