package com.thetaciturnone.taccorpstrinkets.item;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketItem;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Pair;

import java.util.List;
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

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		tooltip.add(Text.translatable("item.taccorpstrinkets.mask_of_silence.tooltip").setStyle(Style.EMPTY.withColor(0xd0c6b6)));
		super.appendTooltip(stack, context, tooltip, type);
	}

}
