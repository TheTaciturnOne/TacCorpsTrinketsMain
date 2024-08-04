package com.thetaciturnone.taccorpstrinkets.utils;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class TaccorpsUtil {
	public static boolean hasEnchantment(Enchantment enchantment, ItemStack stack) {
		return EnchantmentHelper.getLevel(enchantment, stack) > 0;
	}

	public static boolean hasEnchantment(Enchantment enchantment, Entity entity) {
		return entity instanceof LivingEntity living && EnchantmentHelper.getEquipmentLevel(enchantment, living) > 0;
	}
}
