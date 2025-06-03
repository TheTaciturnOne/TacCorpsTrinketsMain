package com.thetaciturnone.taccorpstrinkets.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.thetaciturnone.taccorpstrinkets.TacCorpsTrinkets;
import com.thetaciturnone.taccorpstrinkets.item.QuartziteHammerItem;
import com.thetaciturnone.taccorpstrinkets.item.BaseHammerItem;
import com.thetaciturnone.taccorpstrinkets.registries.TacEnchantmentEffects;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
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

	@Shadow
	public abstract ItemStack getActiveItem();

	@Shadow
	public abstract boolean addStatusEffect(StatusEffectInstance effect);

	@ModifyReturnValue(method = "disablesShield", at = @At("RETURN"))
    public boolean tacCorp$hammerDisablesShield(boolean original) {
        if (this.getMainHandStack().getItem() instanceof QuartziteHammerItem || this.getMainHandStack().getItem() instanceof BaseHammerItem) {
            return true;
        }
		return original;
	}

	@ModifyReturnValue(method = "damage", at = @At("TAIL"))
	private boolean tacCorp$damageDuringCharge(boolean original, DamageSource source, float amount) {
		if (original && EnchantmentHelper.hasAnyEnchantmentsWith(this.getActiveItem(), TacEnchantmentEffects.HAMMER_THROW)) {
			this.addStatusEffect(new StatusEffectInstance(TacCorpsTrinkets.STUNNED, 30, 0, false, false, true));
		}
		return original;
	}

}
