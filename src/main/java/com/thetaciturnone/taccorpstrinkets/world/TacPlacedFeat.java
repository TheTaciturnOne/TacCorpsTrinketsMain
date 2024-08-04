package com.thetaciturnone.taccorpstrinkets.world;

import net.minecraft.util.Holder;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.decorator.*;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacementModifier;
import net.minecraft.world.gen.feature.UndergroundConfiguredFeatures;
import net.minecraft.world.gen.feature.util.PlacedFeatureUtil;

import java.util.List;

public class TacPlacedFeat {
	public static final Holder<PlacedFeature> QUARTZ_GEODE_PLACED = PlacedFeatureUtil.register("quartz_geode_placed",
		TacConfigFeatures.NATURAL_QUARTZ_GEODE, RarityFilterPlacementModifier.create(24),
		InSquarePlacementModifier.getInstance(),
		HeightRangePlacementModifier.createUniform(YOffset.aboveBottom(6), YOffset.fixed(30)),
		BiomePlacementModifier.getInstance());

	private static List<PlacementModifier> modifiers(PlacementModifier countModifier, PlacementModifier heightModifier) {
		return List.of(countModifier, InSquarePlacementModifier.getInstance(), heightModifier, BiomePlacementModifier.getInstance());
	}
	private static List<PlacementModifier> modifiersWithCount(int count, PlacementModifier heightModifier) {
		return modifiers(CountPlacementModifier.create(count), heightModifier);
	}
	private static List<PlacementModifier> modifiersWithRarity(int chance, PlacementModifier heightModifier) {
		return modifiers(RarityFilterPlacementModifier.create(chance), heightModifier);
	}
}
