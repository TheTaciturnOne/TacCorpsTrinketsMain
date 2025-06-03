package com.thetaciturnone.taccorpstrinkets.registries;

import com.thetaciturnone.taccorpstrinkets.TacCorpsTrinkets;
import net.minecraft.component.ComponentType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.*;
import net.minecraft.util.Unit;

import java.util.function.UnaryOperator;

public interface TacEnchantmentEffects {

	RegistryKey<Enchantment> FLINGING = keyOf("flinging");
	RegistryKey<Enchantment> VAULTING = keyOf("vaulting");

	ComponentType<Unit> HAMMER_THROW = registerComponent("hammer_throw", (builder) -> builder.codec(Unit.CODEC));
	ComponentType<Unit> VAULT = registerComponent("vault", (builder) -> builder.codec(Unit.CODEC));


	private static RegistryKey<Enchantment> keyOf(String id) {
		return RegistryKey.of(RegistryKeys.ENCHANTMENT, TacCorpsTrinkets.id(id));
	}

	private static <T> ComponentType<T> registerComponent(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
		return Registry.register(Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, TacCorpsTrinkets.id(id), builderOperator.apply(ComponentType.builder()).build());
	}

	static void registerModEnchantmentEffects() {

	}
}
