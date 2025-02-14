package com.thetaciturnone.taccorpstrinkets;

import com.thetaciturnone.taccorpstrinkets.blocks.entities.PrismaticQuartzBlockEntity;
import com.thetaciturnone.taccorpstrinkets.blocks.entities.TacPlushieBlockEntity;
import com.thetaciturnone.taccorpstrinkets.enchantments.FlingingEnchantment;
import com.thetaciturnone.taccorpstrinkets.enchantments.SlammingEnchantment;
import com.thetaciturnone.taccorpstrinkets.enchantments.StunningEnchantment;
import com.thetaciturnone.taccorpstrinkets.registries.TacBlocks;
import com.thetaciturnone.taccorpstrinkets.registries.TacEntities;
import com.thetaciturnone.taccorpstrinkets.registries.TacItems;
import com.thetaciturnone.taccorpstrinkets.utils.StatusEffectBase;
import com.thetaciturnone.taccorpstrinkets.world.TacConfigFeatures;
import com.thetaciturnone.taccorpstrinkets.world.TacWorldGen;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.item.Item;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class TacCorpsTrinkets implements ModInitializer {
	public static final StatusEffect STUNNED = registerStatusEffect("stunned",
		new StatusEffectBase(StatusEffectCategory.HARMFUL, 0xe6ccc4)
	);
	public static final String MOD_ID = "taccorpstrinkets";
	public static final Logger LOGGER = LoggerFactory.getLogger("Taccorp's Trinkets");
	public static final Identifier TAC_BOOPED_ID = new Identifier("taccorpstrinkets:tacplush_booped");
	public static SoundEvent TAC_BOOPED_SOUND_EVENT = new SoundEvent(TAC_BOOPED_ID);
	public static final Identifier TAC_THROWHIT_ID = new Identifier("taccorpstrinkets:tacplush_hit");
	public static SoundEvent TAC_THROWHIT_SOUND_EVENT = new SoundEvent(TAC_THROWHIT_ID);

	public static final Identifier QUARTZ_CRYSTAL_PLACE_ID = new Identifier("taccorpstrinkets:quartz_crystal_place");
	public static SoundEvent QUARTZ_CRYSTAL_PLACE_EVENT = new SoundEvent(QUARTZ_CRYSTAL_PLACE_ID);
	public static final Identifier QUARTZ_CRYSTAL_STEP_ID = new Identifier("taccorpstrinkets:quartz_crystal_step");
	public static SoundEvent QUARTZ_CRYSTAL_STEP_EVENT = new SoundEvent(QUARTZ_CRYSTAL_STEP_ID);
	public static final Identifier QUARTZ_CRYSTAL_BREAK_ID = new Identifier("taccorpstrinkets:quartz_crystal_break");
	public static SoundEvent QUARTZ_CRYSTAL_BREAK_EVENT = new SoundEvent(QUARTZ_CRYSTAL_BREAK_ID);
	public static final Identifier QUARTZ_CRYSTAL_FALL_ID = new Identifier("taccorpstrinkets:quartz_crystal_fall");
	public static SoundEvent QUARTZ_CRYSTAL_FALL_EVENT = new SoundEvent(QUARTZ_CRYSTAL_FALL_ID);
	public static final Identifier QUARTZ_CRYSTAL_HIT_ID = new Identifier("taccorpstrinkets:quartz_crystal_hit");
	public static SoundEvent QUARTZ_CRYSTAL_HIT_EVENT = new SoundEvent(QUARTZ_CRYSTAL_HIT_ID);
	public static final Identifier PRISMATIC_HUM_ID = new Identifier("taccorpstrinkets:prismatic_hum");
	public static SoundEvent PRISMATIC_HUM_EVENT = new SoundEvent(PRISMATIC_HUM_ID);

	public static final Identifier HAMMER_SLAM_ID = new Identifier("taccorpstrinkets:hammer_slammed");
	public static SoundEvent HAMMER_SLAMMED = new SoundEvent(HAMMER_SLAM_ID);
	public static final Identifier WEAK_HAMMER_SLAM_ID = new Identifier("taccorpstrinkets:weak_hammer_slammed");
	public static SoundEvent WEAK_HAMMER_SLAMMED = new SoundEvent(WEAK_HAMMER_SLAM_ID);
	public static final Identifier HAMMER_THROW_ID = new Identifier("taccorpstrinkets:hammer_throw");
	public static SoundEvent HAMMER_THROW = new SoundEvent(HAMMER_THROW_ID);
	public static final Identifier HAMMER_SHOCKWAVE_ID = new Identifier("taccorpstrinkets:hammer_shockwave");
	public static SoundEvent HAMMER_SHOCKWAVE = new SoundEvent(HAMMER_SHOCKWAVE_ID);
	public static final Identifier HAMMER_WHIRRING_ID = new Identifier("taccorpstrinkets:hammer_whir");
	public static SoundEvent HAMMER_WHIRRING = new SoundEvent(HAMMER_WHIRRING_ID);
	public static final Identifier HAMMER_POWERSLAM_ID = new Identifier("taccorpstrinkets:hammer_powerslam");
	public static SoundEvent HAMMER_POWERSLAM = new SoundEvent(HAMMER_POWERSLAM_ID);
	public static final Identifier HAMMER_SHATTER_ID = new Identifier("taccorpstrinkets:hammer_shatter");
	public static SoundEvent HAMMER_SHATTER = new SoundEvent(HAMMER_SHATTER_ID);

	public static final Enchantment STUNNING = new StunningEnchantment(Enchantment.Rarity.RARE, EnchantmentTarget.DIGGER, EquipmentSlot.MAINHAND);
	public static final Enchantment FLINGING = new FlingingEnchantment(Enchantment.Rarity.RARE, EnchantmentTarget.DIGGER, EquipmentSlot.MAINHAND);
	public static final Enchantment SLAMMING = new SlammingEnchantment(Enchantment.Rarity.RARE, EnchantmentTarget.DIGGER, EquipmentSlot.MAINHAND);
	public static final Enchantment VAULTING = new SlammingEnchantment(Enchantment.Rarity.RARE, EnchantmentTarget.DIGGER, EquipmentSlot.MAINHAND);
	public static final Enchantment BOOSTING = new SlammingEnchantment(Enchantment.Rarity.RARE, EnchantmentTarget.DIGGER, EquipmentSlot.MAINHAND);
	public static final DefaultParticleType HAMMER_SLAM = FabricParticleTypes.simple();
	public static final DefaultParticleType HAMMER_WAVE = FabricParticleTypes.simple();
	public static BlockEntityType<PrismaticQuartzBlockEntity> PRISMATIC_ENTITY;
	public static BlockEntityType<TacPlushieBlockEntity> TAC_PLUSH_BLOCK_ENTITY;
	public static final TagKey<Block> LIGHT_SOURCE_BLOCK_TAG = TagKey.of(Registry.BLOCK_KEY, new Identifier("taccorpstrinkets", "light_sources"));

	@Override
	public void onInitialize() {
		TacBlocks.registerModBlocks();
		TacItems.registerModItems();
		TacWorldGen.generateGeode();
		TacConfigFeatures.registerConfiguredFeatures();
		Registry.register(Registry.SOUND_EVENT, TAC_BOOPED_ID, TAC_BOOPED_SOUND_EVENT);
		Registry.register(Registry.SOUND_EVENT, TAC_THROWHIT_ID, TAC_THROWHIT_SOUND_EVENT);
		Registry.register(Registry.SOUND_EVENT, HAMMER_SLAM_ID, HAMMER_SLAMMED);
		Registry.register(Registry.SOUND_EVENT, WEAK_HAMMER_SLAM_ID, WEAK_HAMMER_SLAMMED);
		Registry.register(Registry.SOUND_EVENT, HAMMER_THROW_ID, HAMMER_THROW);
		Registry.register(Registry.SOUND_EVENT, HAMMER_SHOCKWAVE_ID, HAMMER_SHOCKWAVE);
		Registry.register(Registry.SOUND_EVENT, HAMMER_POWERSLAM_ID, HAMMER_POWERSLAM);
		Registry.register(Registry.SOUND_EVENT, HAMMER_WHIRRING_ID, HAMMER_WHIRRING);
		Registry.register(Registry.SOUND_EVENT, HAMMER_SHATTER_ID, HAMMER_SHATTER);
		Registry.register(Registry.SOUND_EVENT, QUARTZ_CRYSTAL_PLACE_ID, QUARTZ_CRYSTAL_PLACE_EVENT);
		Registry.register(Registry.SOUND_EVENT, QUARTZ_CRYSTAL_BREAK_ID, QUARTZ_CRYSTAL_BREAK_EVENT);
		Registry.register(Registry.SOUND_EVENT, QUARTZ_CRYSTAL_STEP_ID, QUARTZ_CRYSTAL_STEP_EVENT);
		Registry.register(Registry.SOUND_EVENT, QUARTZ_CRYSTAL_HIT_ID, QUARTZ_CRYSTAL_HIT_EVENT);
		Registry.register(Registry.SOUND_EVENT, QUARTZ_CRYSTAL_FALL_ID, QUARTZ_CRYSTAL_FALL_EVENT);
		Registry.register(Registry.SOUND_EVENT, PRISMATIC_HUM_ID, PRISMATIC_HUM_EVENT);
		Registry.register(Registry.ENCHANTMENT, new Identifier("taccorpstrinkets", "stunning"), STUNNING);
		Registry.register(Registry.ENCHANTMENT, new Identifier("taccorpstrinkets", "flinging"), FLINGING);
		Registry.register(Registry.ENCHANTMENT, new Identifier("taccorpstrinkets", "slamming"), SLAMMING);
		Registry.register(Registry.ENCHANTMENT, new Identifier("taccorpstrinkets", "vaulting"), VAULTING);
		Registry.register(Registry.ENCHANTMENT, new Identifier("taccorpstrinkets", "boosting"), BOOSTING);

		Registry.register(Registry.PARTICLE_TYPE, new Identifier("taccorpstrinkets", "hammer_slam"), HAMMER_SLAM);
		Registry.register(Registry.PARTICLE_TYPE, new Identifier("taccorpstrinkets", "hammer_wave"), HAMMER_WAVE);
		PRISMATIC_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE,
			new Identifier(TacCorpsTrinkets.MOD_ID, "prismatic_quartz"),
			FabricBlockEntityTypeBuilder.create(PrismaticQuartzBlockEntity::new,
				TacBlocks.PRISMATIC_QUARTZ_CRYSTAL).build(null));
		TAC_PLUSH_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE,
			new Identifier(TacCorpsTrinkets.MOD_ID, "tac_plush"),
			FabricBlockEntityTypeBuilder.create(TacPlushieBlockEntity::new,
				TacBlocks.TAC_PLUSHIE).build(null));
		TacEntities.init();
	}

	@SuppressWarnings("OptionalGetWithoutIsPresent")
	public static TrinketComponent getTrinkets(LivingEntity entity) {
		return TrinketsApi.getTrinketComponent(entity).get();
	}
	public static boolean hasTrinket(LivingEntity entity, Item trinket) {
		return getTrinkets(entity).isEquipped(trinket);
	}

	private static <T extends StatusEffect> T registerStatusEffect(String name, T effect) {
		Registry.register(Registry.STATUS_EFFECT, new Identifier(MOD_ID, name), effect);
		return effect;
	}
}
