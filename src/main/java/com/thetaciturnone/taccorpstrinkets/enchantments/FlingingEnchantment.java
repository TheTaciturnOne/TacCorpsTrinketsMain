package com.thetaciturnone.taccorpstrinkets.enchantments;

import com.thetaciturnone.taccorpstrinkets.item.QuartziteHammerItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public class FlingingEnchantment extends Enchantment {

	public FlingingEnchantment(Rarity weight, EnchantmentTarget type, EquipmentSlot... slotTypes) {
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
}
