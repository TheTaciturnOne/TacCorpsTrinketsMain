package com.thetaciturnone.taccorpstrinkets.mixin;

import com.thetaciturnone.taccorpstrinkets.item.QuartziteHammerItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
	@Shadow
	public abstract ItemStack getMainHandStack();

	protected LivingEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super();
    }

    @Inject(method = "canDisableShield", at = @At("HEAD"), cancellable = true)
    public void trinkets$disableshield(CallbackInfoReturnable<Boolean> cir) {
        if (this.getMainHandStack().getItem() instanceof QuartziteHammerItem) {
            cir.setReturnValue(true);
        }
    }
}
