package com.thetaciturnone.taccorpstrinkets.item;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketItem;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Pair;
import xyz.amymialee.mialeemisc.util.MialeeMath;

import java.util.Optional;

public class SilentMaskItem extends TrinketItem {
	public SilentMaskItem(Settings settings) {
		super(settings);
	}

	public static boolean isWearingMask(LivingEntity livingEntity) {
		return getWornMask(livingEntity) != ItemStack.EMPTY;
	}

	public static ItemStack getWornMask(LivingEntity livingEntity) {
		Optional<TrinketComponent> component = TrinketsApi.getTrinketComponent(livingEntity);
		if (component.isPresent()) {
			for (Pair<SlotReference, ItemStack> pair : component.get().getAllEquipped()) {
				if (pair.getRight().getItem() instanceof SilentMaskItem) {
					return pair.getRight();
				}
			}
		}
		return ItemStack.EMPTY;
	}

	public static int getOffset(ItemStack stack) {
		if (stack.getNbt() == null) {
			return 0;
		}
		return stack.getNbt().getInt("offset");
	}
}
