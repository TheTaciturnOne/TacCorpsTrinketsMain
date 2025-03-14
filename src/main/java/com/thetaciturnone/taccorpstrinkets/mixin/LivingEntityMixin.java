package com.thetaciturnone.taccorpstrinkets.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.thetaciturnone.taccorpstrinkets.TacCorpsTrinkets;
import com.thetaciturnone.taccorpstrinkets.item.QuartziteHammerItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
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
        if (this.getMainHandStack().getItem() instanceof QuartziteHammerItem) {
            return true;
        }
		return original;
	}

}
