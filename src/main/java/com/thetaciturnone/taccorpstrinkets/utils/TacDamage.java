package com.thetaciturnone.taccorpstrinkets.utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.ProjectileDamageSource;
import org.jetbrains.annotations.Nullable;

public class TacDamage {
	public static DamageSource hammer_throw(Entity trident, @Nullable Entity attacker) {
		return (new ProjectileDamageSource("hammer_throw", trident, attacker)).setProjectile();
	}

	public static final DamageSource HAMMER_SHOCKWAVE = new DamageSource("hammer_shockwave");
	public static final DamageSource HAMMER_POWERSALM = new DamageSource("hammer_powerslam");
}
