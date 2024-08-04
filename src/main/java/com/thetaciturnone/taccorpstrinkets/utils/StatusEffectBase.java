package com.thetaciturnone.taccorpstrinkets.utils;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;

public class StatusEffectBase extends StatusEffect {
	public StatusEffectBase(StatusEffectType category, int color) {
		super(category, color);
	}
}
