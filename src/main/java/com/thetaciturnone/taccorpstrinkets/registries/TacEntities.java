package com.thetaciturnone.taccorpstrinkets.registries;

import com.thetaciturnone.taccorpstrinkets.TacCorpsTrinkets;
import com.thetaciturnone.taccorpstrinkets.entity.ThrownHammerEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;

public class TacEntities {
	public static EntityType<ThrownHammerEntity> THROWN_HAMMER;

	public static void init() {
		THROWN_HAMMER = register("thrown_hammer", createEntityType(ThrownHammerEntity::new));
	}

	private static <T extends Entity> EntityType<T> register(String s, EntityType<T> bombEntityType) {
		return Registry.register(Registry.ENTITY_TYPE, TacCorpsTrinkets.MOD_ID + ":" + s, bombEntityType);
	}

	private static <T extends Entity> EntityType<T> createEntityType(EntityType.EntityFactory<T> factory) {
		return FabricEntityTypeBuilder.create(SpawnGroup.MISC, factory).dimensions(EntityDimensions.changing(0.5f, 0.5f)).trackRangeBlocks(4).trackedUpdateRate(20).build();
	}
}
