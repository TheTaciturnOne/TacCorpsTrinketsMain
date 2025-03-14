package com.thetaciturnone.taccorpstrinkets.registries;

import com.thetaciturnone.taccorpstrinkets.TacCorpsTrinkets;
import com.thetaciturnone.taccorpstrinkets.entity.ThrownHammerEntity;
import com.thetaciturnone.taccorpstrinkets.entity.ThrownTacEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class TacEntities {
	public static EntityType<ThrownHammerEntity> THROWN_HAMMER = register("thrown_hammer", createEntityType(ThrownHammerEntity::new, 0.75f));
	public static EntityType<ThrownTacEntity> THROWN_TAC = register("thrown_tac", createEntityType(ThrownTacEntity::new, 0.4f));

	public static void registerModEntities() { // this method can be blank

	}

	private static <T extends Entity> EntityType<T> register(String name, EntityType<T> bombEntityType) {
		return Registry.register(Registries.ENTITY_TYPE, TacCorpsTrinkets.id(name), bombEntityType);
	}

	private static <T extends Entity> EntityType<T> createEntityType(EntityType.EntityFactory<T> factory, float size) {
		return EntityType.Builder.create(factory, SpawnGroup.MISC).dimensions(size, size).maxTrackingRange(64).trackingTickInterval(20).build();
	}
}
