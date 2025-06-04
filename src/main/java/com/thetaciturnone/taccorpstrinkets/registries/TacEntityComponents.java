package com.thetaciturnone.taccorpstrinkets.registries;

import com.thetaciturnone.taccorpstrinkets.TacCorpsTrinkets;
import com.thetaciturnone.taccorpstrinkets.component.HammerUserComponent;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;

public class TacEntityComponents implements EntityComponentInitializer {

	public static final ComponentKey<HammerUserComponent> HAMMER_USER = ComponentRegistry.getOrCreate(TacCorpsTrinkets.id("hammer_user"), HammerUserComponent.class);

	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry entityComponentFactoryRegistry) {
		entityComponentFactoryRegistry.registerForPlayers(HAMMER_USER, HammerUserComponent::new, RespawnCopyStrategy.NEVER_COPY);
	}
}
