package com.thetaciturnone.taccorpstrinkets.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.thetaciturnone.taccorpstrinkets.item.QuartziteHammerItem;
import com.thetaciturnone.taccorpstrinkets.item.ShatteredHammerItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@Shadow
	public abstract ItemStack getMainHandStack();

	@ModifyReturnValue(method = "disablesShield", at = @At("RETURN"))
    public boolean tacCorp$hammerDisablesShield(boolean original) {
        if (this.getMainHandStack().getItem() instanceof QuartziteHammerItem || this.getMainHandStack().getItem() instanceof ShatteredHammerItem) {
            return true;
        }
		return original;
	}

}
