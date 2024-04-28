package me.doclic.temflhos.mixin;

import me.doclic.temflhos.module.ModuleManager;
import me.doclic.temflhos.util.CommonFieldsKt;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDragonEgg;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static me.doclic.temflhos.util.CommonFieldsKt.getLocalPlayer;
import static me.doclic.temflhos.util.CommonFieldsKt.getMc;
import static me.doclic.temflhos.util.CommonFunctionsKt.tChat;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {
    @Inject(method = "sendClickBlockToController", at = @At("HEAD"), cancellable = true)
    private void a(boolean leftClick, CallbackInfo ci) {
        if (!ModuleManager.INSTANCE.getRegistry().get("safe_interact").getEnabled().getValue()) return;
        MovingObjectPosition target = getMc().objectMouseOver;
        if (target == null || target.typeOfHit != MovingObjectType.BLOCK) return;
        Block block = getMc().theWorld.getBlockState(target.getBlockPos()).getBlock();
        if (block != Blocks.dragon_egg) return;
        tChat(EnumChatFormatting.GREEN+"Safe interact prevented you from teleporting that block");
        ci.cancel();
    }

}
