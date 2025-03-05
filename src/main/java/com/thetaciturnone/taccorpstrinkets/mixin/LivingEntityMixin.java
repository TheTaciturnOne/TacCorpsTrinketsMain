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

	@Shadow
	public abstract boolean hasStatusEffect(StatusEffect effect);

	@ModifyReturnValue(method = "disablesShield", at = @At("RETURN"))
    public boolean tacCorp$hammerDisablesShield(boolean original) {
        if (this.getMainHandStack().getItem() instanceof QuartziteHammerItem) {
            return true;
        }
		return original;
	}

	@WrapOperation(method = "tickStatusEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V"))
	private void tacCorp$stunnedParticles(World instance, ParticleEffect parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ, Operation<Void> original) {
		if (this.hasStatusEffect(TacCorpsTrinkets.STUNNED)) {
			original.call(instance, ParticleTypes.END_ROD, x, y, z, 0.0, 0.0, 0.0); // if you use the original velocity they go flying lmao, we can add our own velocity if we want, maybe something that flies slightly outward from the player?
		}
		else {
			original.call(instance, parameters, x, y, z, velocityX, velocityY, velocityZ);
		}
	}
}
