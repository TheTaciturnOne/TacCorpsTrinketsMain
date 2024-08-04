package com.thetaciturnone.taccorpstrinkets.enchantments;

import com.thetaciturnone.taccorpstrinkets.TacCorpsTrinkets;
import com.thetaciturnone.taccorpstrinkets.item.QuartziteHammerItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;

public class StunningEnchantment extends Enchantment {

	public StunningEnchantment(Enchantment.Rarity weight, EnchantmentTarget type, EquipmentSlot... slotTypes) {
		super (weight, type, slotTypes);
	}

	@Override
	public int getMaxPower(int level) {
		return 1;
	}



	@Override
	public boolean isAcceptableItem(ItemStack stack) {
		return stack.getItem() instanceof QuartziteHammerItem;
	}
	@Override
	public void onTargetDamaged(LivingEntity user, Entity target, int level) {
		if(target instanceof LivingEntity) {
			((LivingEntity) target).addStatusEffect(new StatusEffectInstance(TacCorpsTrinkets.STUNNED, 6, 1, false, false));
		}

		super.onTargetDamaged(user, target, level);
	}
}
