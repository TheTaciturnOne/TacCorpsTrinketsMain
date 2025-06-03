package com.thetaciturnone.taccorpstrinkets;

import com.thetaciturnone.taccorpstrinkets.blocks.entities.PrismaticQuartzBlockEntity;
import com.thetaciturnone.taccorpstrinkets.blocks.entities.TacPlushieBlockEntity;
import com.thetaciturnone.taccorpstrinkets.registries.*;
import com.thetaciturnone.taccorpstrinkets.supporter.SupporterUtils;
import com.thetaciturnone.taccorpstrinkets.utils.StatusEffectBase;
import com.thetaciturnone.taccorpstrinkets.world.TacWorldGen;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.block.Block;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TacCorpsTrinkets implements ModInitializer {
	private static final Identifier QUARTZ_ID
		= Identifier.of("minecraft", "blocks/nether_quartz_ore");
	public static final String MOD_ID = "taccorpstrinkets";
	public static final Logger LOGGER = LoggerFactory.getLogger("Taccorp's Trinkets");
	public static SoundEvent TAC_BOOPED_SOUND_EVENT = Registry.register(Registries.SOUND_EVENT, id("tacplush_booped"), SoundEvent.of(id("tacplush_booped")));
	public static SoundEvent TAC_THROWHIT_SOUND_EVENT = Registry.register(Registries.SOUND_EVENT, id("tacplush_hit"), SoundEvent.of(id("tacplush_hit")));
	public static SoundEvent TAC_SCREAM_SOUND_EVENT = Registry.register(Registries.SOUND_EVENT, id("tacplush_scream"), SoundEvent.of(id("tacplush_scream")));

	public static SoundEvent QUARTZ_CRYSTAL_PLACE_EVENT = Registry.register(Registries.SOUND_EVENT, id("quartz_crystal_place"), SoundEvent.of(id("quartz_crystal_place")));
	public static SoundEvent QUARTZ_CRYSTAL_STEP_EVENT = Registry.register(Registries.SOUND_EVENT, id("quartz_crystal_step"), SoundEvent.of(id("quartz_crystal_step")));
	public static SoundEvent QUARTZ_CRYSTAL_BREAK_EVENT = Registry.register(Registries.SOUND_EVENT, id("quartz_crystal_break"), SoundEvent.of(id("quartz_crystal_break")));
	public static SoundEvent QUARTZ_CRYSTAL_FALL_EVENT = Registry.register(Registries.SOUND_EVENT, id("quartz_crystal_fall"), SoundEvent.of(id("quartz_crystal_fall")));
	public static SoundEvent QUARTZ_CRYSTAL_HIT_EVENT = Registry.register(Registries.SOUND_EVENT, id("quartz_crystal_hit"), SoundEvent.of(id("quartz_crystal_hit")));
	public static SoundEvent PRISMATIC_HUM_EVENT = Registry.register(Registries.SOUND_EVENT, id("prismatic_hum"), SoundEvent.of(id("prismatic_hum")));

	public static SoundEvent HAMMER_SLAMMED = Registry.register(Registries.SOUND_EVENT, id("hammer_slammed"), SoundEvent.of(id("hammer_slammed")));
	public static SoundEvent HAMMER_THROW = Registry.register(Registries.SOUND_EVENT, id("hammer_throw"), SoundEvent.of(id("hammer_throw")));
	public static SoundEvent HAMMER_SHOCKWAVE = Registry.register(Registries.SOUND_EVENT, id("hammer_shockwave"), SoundEvent.of(id("hammer_shockwave")));
	public static SoundEvent HAMMER_WHIRRING = Registry.register(Registries.SOUND_EVENT, id("hammer_whir"), SoundEvent.of(id("hammer_whir")));
	public static SoundEvent HAMMER_POWERSLAM = Registry.register(Registries.SOUND_EVENT, id("hammer_powerslam"), SoundEvent.of(id("hammer_powerslam")));
	public static SoundEvent HAMMER_SHATTER = Registry.register(Registries.SOUND_EVENT, id("hammer_shatter"), SoundEvent.of(id("hammer_shatter")));

	public static final RegistryEntry<StatusEffect> STUNNED = registerStatusEffect("stunned",
		new StatusEffectBase(StatusEffectCategory.HARMFUL, 0xe6ccc4));
	public static final SimpleParticleType HAMMER_SLAM = FabricParticleTypes.simple();
	public static final SimpleParticleType HAMMER_WAVE = FabricParticleTypes.simple();
	public static BlockEntityType<PrismaticQuartzBlockEntity> PRISMATIC_ENTITY;
	public static BlockEntityType<TacPlushieBlockEntity> TAC_PLUSH_BLOCK_ENTITY;
	public static final TagKey<Block> LIGHT_SOURCE_BLOCK_TAG = TagKey.of(Registries.BLOCK.getKey(), id("light_sources"));
	public static final TagKey<Block> BREAKABLE_GLASS_TAG = TagKey.of(Registries.BLOCK.getKey(), id("breakable_glass"));

	@Override
	public void onInitialize() {
		new Thread(SupporterUtils::fetchPlayers).start();

		TacCorpsTrinkets.LOGGER.info("call me a hammer the way i SLAM DUNKED THIS MOD (fire emoji)");
		TacBlocks.registerModBlocks();
		TacItems.registerModItems();
		TacEntities.registerModEntities();
		TacWorldGen.registerGeode();
		TacItemComponents.registerModComponents();
		TacEnchantmentEffects.registerModEnchantmentEffects();

		Registry.register(Registries.PARTICLE_TYPE, id("hammer_slam"), HAMMER_SLAM);
		Registry.register(Registries.PARTICLE_TYPE, id("hammer_wave"), HAMMER_WAVE);
		PRISMATIC_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
			TacCorpsTrinkets.id("prismatic_quartz"),
			BlockEntityType.Builder.create(PrismaticQuartzBlockEntity::new,
				TacBlocks.PRISMATIC_QUARTZ_CRYSTAL).build(null));
		TAC_PLUSH_BLOCK_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE,
			TacCorpsTrinkets.id("tac_plush"),
			BlockEntityType.Builder.create(TacPlushieBlockEntity::new,
				TacBlocks.TAC_PLUSHIE).build(null));

		ServerEntityEvents.ENTITY_UNLOAD.register((entity, serverWorld) -> { // woah events world so beau- NOOOOOO TAC PLUSHIEEEE THIS IS JUST LIKE PORTAL
			if (entity.getRemovalReason() == Entity.RemovalReason.DISCARDED) {
				if (entity instanceof ItemEntity itemEntity && itemEntity.getStack().isOf(TacBlocks.TAC_PLUSHIE.asItem())) {
					entity.playSound(TacCorpsTrinkets.TAC_SCREAM_SOUND_EVENT, 0.4f, 1.0f);
				}
			}
		});

		DispenserBlock.registerProjectileBehavior(TacBlocks.TAC_PLUSHIE.asItem());

		LootTableEvents.MODIFY.register((key, tableBuilder, source, registry) -> {
			if(QUARTZ_ID.equals(key.getValue())) {
				LootPool.Builder poolBuilder = LootPool.builder()
					.rolls(ConstantLootNumberProvider.create(1))
					.conditionally(RandomChanceLootCondition.builder(0.25f))
					.with(ItemEntry.builder(TacItems.PRISMATIC_QUARTZ_SHARD))
					.apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 2.0f)).build());

				tableBuilder.pool(poolBuilder.build());
			}
		});

	}

	private static RegistryEntry<StatusEffect> registerStatusEffect(String name, StatusEffect effect) {
		return Registry.registerReference(Registries.STATUS_EFFECT, id(name), effect);
	}

	public static Identifier id(String name) {
		return Identifier.of(MOD_ID, name);
	}
}
