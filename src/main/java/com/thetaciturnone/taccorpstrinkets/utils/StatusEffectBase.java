package com.thetaciturnone.taccorpstrinkets.utils;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class StatusEffectBase extends StatusEffect {
	public StatusEffectBase(StatusEffectCategory category, int color) {
		super(category, color);
	}
}
