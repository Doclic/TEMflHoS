package me.doclic.temflhos.mixin;

import me.doclic.temflhos.event.ListenerManager;
import me.doclic.temflhos.event.SplashTextEvent;
import net.minecraft.client.gui.GuiMainMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMainMenu.class)
public abstract class MixinGuiMainMenu {
    @Shadow private String splashText;

    @Inject(method = "<init>", at = @At("RETURN"), remap = false)
    private void init(CallbackInfo ci) {
        final SplashTextEvent e = new SplashTextEvent(splashText);
        ListenerManager.INSTANCE.getRegistry().forEach(listener -> listener.onSplashText(e));
        splashText = e.getSplashText();
    }
}
