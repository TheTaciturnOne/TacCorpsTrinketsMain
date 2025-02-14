package com.thetaciturnone.taccorpstrinkets.world;

import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

public class TacPlacedFeat {
	public static final RegistryEntry<PlacedFeature> QUARTZ_GEODE_PLACED = PlacedFeatures.register("quartz_geode_placed",
		TacConfigFeatures.NATURAL_QUARTZ_GEODE, RarityFilterPlacementModifier.of(24),
		SquarePlacementModifier.of(),
		HeightRangePlacementModifier.uniform(YOffset.aboveBottom(6), YOffset.fixed(30)),
		BiomePlacementModifier.of());

	private static List<PlacementModifier> modifiers(PlacementModifier countModifier, PlacementModifier heightModifier) {
		return List.of(countModifier, SquarePlacementModifier.of(), heightModifier, BiomePlacementModifier.of());
	}
	private static List<PlacementModifier> modifiersWithCount(int count, PlacementModifier heightModifier) {
		return modifiers(CountPlacementModifier.of(count), heightModifier);
	}
	private static List<PlacementModifier> modifiersWithRarity(int chance, PlacementModifier heightModifier) {
		return modifiers(RarityFilterPlacementModifier.of(chance), heightModifier);
	}
}
