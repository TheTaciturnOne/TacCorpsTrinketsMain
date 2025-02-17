package com.thetaciturnone.taccorpstrinkets.utils;

import com.thetaciturnone.taccorpstrinkets.TacCorpsTrinkets;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class TacDamage {

	public static final RegistryKey<DamageType> HAMMER_SHOCKWAVE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, TacCorpsTrinkets.id("hammer_shockwave"));
	public static final RegistryKey<DamageType> HAMMER_POWERSLAM = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, TacCorpsTrinkets.id("hammer_powerslam"));

	public static DamageSource create(World world, RegistryKey<DamageType> key) {
		return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key));
	}

	public static DamageSource create(World world, RegistryKey<DamageType> key, @Nullable Entity attacker) {
		return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key), attacker);
	}

	public static DamageSource create(World world, RegistryKey<DamageType> key, @Nullable Entity source, @Nullable Entity attacker) {
		return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key), source, attacker);
	}

}
