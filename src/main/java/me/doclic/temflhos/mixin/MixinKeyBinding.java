package me.doclic.temflhos.mixin;

import me.doclic.temflhos.module.ModuleManager;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

@Mixin(Minecraft.class)
public abstract class MixinKeyBinding {
    private Field readBufferField = null;

    @Inject(method = "runTick", at = @At("HEAD"))
    private void onRunTick(CallbackInfo ci) {
        try {
            if (readBufferField == null) {
                readBufferField = Keyboard.class.getDeclaredField("readBuffer");
                readBufferField.setAccessible(true);
            }

            ByteBuffer readBuffer = (ByteBuffer) readBufferField.get(null);
            readBuffer.mark();
            while (Keyboard.next()) {
                if(!Keyboard.getEventKeyState()) continue;
                if(!Keyboard.isKeyDown(Keyboard.KEY_RMENU)) continue; // KEY_RMENU is right alt

                ModuleManager.INSTANCE.getRegistry().forEach((id, module) -> {
                    if(module.getKey().getValue() == Keyboard.getEventKey()) {
                        module.getEnabled().setValue(!module.getEnabled().getValue());
                        module.sendStateUpdateMsg();
                    }
                });
            }
            readBuffer.reset();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
