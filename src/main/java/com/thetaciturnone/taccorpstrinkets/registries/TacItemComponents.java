package com.thetaciturnone.taccorpstrinkets.registries;

import com.thetaciturnone.taccorpstrinkets.TacCorpsTrinkets;
import com.thetaciturnone.taccorpstrinkets.item.HammerVariantComponent;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.function.UnaryOperator;

public class TacItemComponents {

	public static final ComponentType<HammerVariantComponent> HAMMER_VARIANT = registerComponent("hammer_variant",
		(builder) -> ComponentType.<HammerVariantComponent>builder().codec(HammerVariantComponent.CODEC).packetCodec(HammerVariantComponent.PACKET_CODEC).cache());

	public static void registerModComponents() {

	}

	private static <T> ComponentType<T> registerComponent(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
		return Registry.register(Registries.DATA_COMPONENT_TYPE, TacCorpsTrinkets.id(id), (builderOperator.apply(ComponentType.builder())).build());
	}
}
