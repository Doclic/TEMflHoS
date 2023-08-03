package me.doclic.temflhos.mixin.crashpatch;

import me.doclic.temflhos.module.ModuleManager;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.properties.PropertyInteger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(BlockAnvil.class)
public class MixinBlockAnvil {
    @Final
    @Shadow
    public static PropertyInteger DAMAGE;

    @ModifyVariable(method = "getStateFromMeta", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    public int getStateFromMeta(int meta){
        if (!ModuleManager.INSTANCE.getRegistry().get("crash_patch").getEnabled().getValue()) return meta;
        if (!DAMAGE.getAllowedValues().contains((meta & 0xF) >> 2)) return 0;
        return meta;
    }
    @ModifyVariable(method = "onBlockPlaced", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    public int onBlockPlaced(int meta){
        if (!ModuleManager.INSTANCE.getRegistry().get("crash_patch").getEnabled().getValue()) return meta;
        if (!DAMAGE.getAllowedValues().contains(meta >> 2)) return 0;
        return meta;
    }

}
