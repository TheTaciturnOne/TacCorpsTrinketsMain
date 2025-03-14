package com.thetaciturnone.taccorpstrinkets.registries;

import com.mojang.serialization.Codec;
import com.thetaciturnone.taccorpstrinkets.TacCorpsTrinkets;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.function.UnaryOperator;

public class TacItemComponents {

	public static final ComponentType<Integer> HAMMER_VARIANT = Registry.register(Registries.DATA_COMPONENT_TYPE, TacCorpsTrinkets.id("hammer_variant"),
		ComponentType.<Integer>builder().codec(Codec.INT).build());

	public static void registerModComponents() {

	}

	private static <T> ComponentType<T> registerComponent(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
		return Registry.register(Registries.DATA_COMPONENT_TYPE, TacCorpsTrinkets.id(id), (builderOperator.apply(ComponentType.builder())).build());
	}
}
