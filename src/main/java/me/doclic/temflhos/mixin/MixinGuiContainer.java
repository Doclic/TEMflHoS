package me.doclic.temflhos.mixin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import me.doclic.temflhos.module.ModuleManager;

@Mixin(GuiContainer.class)
public abstract class MixinGuiContainer {
    @Inject(at = @At("HEAD"), method = "handleMouseClick", cancellable = true)
    public void handleMouseClick(Slot slotIn, int slotId, int clickedButton, int clickType, CallbackInfo ci) {
        if (slotIn == null || slotIn.getStack() == null) return;
        if (!ModuleManager.INSTANCE.getRegistry().get("ghost_item").getEnabled().getValue()) return;
        ci.cancel();
        ItemStack clickedItem = slotIn.getStack();
        Minecraft.getMinecraft().thePlayer.inventory.addItemStackToInventory(clickedItem);
    }
}
