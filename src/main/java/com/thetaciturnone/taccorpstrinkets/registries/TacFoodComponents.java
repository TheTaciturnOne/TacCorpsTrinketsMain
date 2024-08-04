package com.thetaciturnone.taccorpstrinkets.registries;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;

public class TacFoodComponents {
	public static final FoodComponent SPECTRE_BERRY = new FoodComponent.Builder().hunger(2).saturationModifier(0.2f).statusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 250, 2, false, false), 0.6F).build();
}
