package com.thetaciturnone.taccorpstrinkets.world;

import com.thetaciturnone.taccorpstrinkets.TacCorpsTrinkets;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;

public class TacWorldGen {

	public static final RegistryKey<ConfiguredFeature<?, ?>> NATURAL_QUARTZ_GEODE_CONFIGURED_FEATURE = registerConfiguredFeature("quartzite_geode");
	public static final RegistryKey<PlacedFeature> NATURAL_QUARTZ_GEODE_PLACED_FEATURE = registerPlacedFeature("quartzite_geode");

	public static void registerGeode() {

		BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(),
			GenerationStep.Feature.UNDERGROUND_DECORATION, NATURAL_QUARTZ_GEODE_PLACED_FEATURE);

		TacCorpsTrinkets.LOGGER.debug("Spwaming geod frum " + TacCorpsTrinkets.MOD_ID);
	}

	public static RegistryKey<ConfiguredFeature<?, ?>> registerConfiguredFeature(String id) {
		return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, TacCorpsTrinkets.id(id));
	}

	public static RegistryKey<PlacedFeature> registerPlacedFeature(String id) {
		return RegistryKey.of(RegistryKeys.PLACED_FEATURE, TacCorpsTrinkets.id(id));
	}
}
