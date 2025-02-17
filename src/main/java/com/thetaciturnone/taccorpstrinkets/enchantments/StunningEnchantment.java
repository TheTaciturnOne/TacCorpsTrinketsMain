package com.thetaciturnone.taccorpstrinkets.enchantments;

import com.thetaciturnone.taccorpstrinkets.TacCorpsTrinkets;
import com.thetaciturnone.taccorpstrinkets.registries.TacItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class StunningEnchantment extends Enchantment {

	public StunningEnchantment(Enchantment.Rarity weight, EnchantmentTarget type, EquipmentSlot... slotTypes) {
		super (weight, type, slotTypes);
	}

	@Override
	public int getMaxPower(int level) {
		return 1;
	}

	@Override
	public void onTargetDamaged(LivingEntity user, Entity target, int level) {
		if(target instanceof LivingEntity targetEntity) { // TODO: add overlay or some visual indicator of stun that isn't the effect icon?
			targetEntity.addStatusEffect(new StatusEffectInstance(TacCorpsTrinkets.STUNNED, MathHelper.clamp(15 * level, 15, 150), 0, false, false));
		}
		super.onTargetDamaged(user, target, level);
	}

	@Override
	public boolean isAcceptableItem(ItemStack stack) {
		return (stack.isOf(TacItems.QUARTZITE_HAMMER) || stack.isOf(TacItems.SHATTERED_QUARTZITE_HAMMER));
	}
}
