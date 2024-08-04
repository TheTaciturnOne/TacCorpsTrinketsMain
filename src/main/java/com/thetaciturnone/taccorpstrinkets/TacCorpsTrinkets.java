package com.thetaciturnone.taccorpstrinkets;

import com.thetaciturnone.taccorpstrinkets.registries.TacBlocks;
import com.thetaciturnone.taccorpstrinkets.enchantments.FlingingEnchantment;
import com.thetaciturnone.taccorpstrinkets.enchantments.SlammingEnchantment;
import com.thetaciturnone.taccorpstrinkets.enchantments.StunningEnchantment;
import com.thetaciturnone.taccorpstrinkets.registries.TacEntities;
import com.thetaciturnone.taccorpstrinkets.registries.TacItems;
import com.thetaciturnone.taccorpstrinkets.utils.StatusEffectBase;
import com.thetaciturnone.taccorpstrinkets.world.TacConfigFeatures;
import com.thetaciturnone.taccorpstrinkets.world.TacWorldGen;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.item.Item;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class TacCorpsTrinkets implements ModInitializer {
	public static final StatusEffect STUNNED = registerStatusEffect("stunned",
		new StatusEffectBase(StatusEffectType.HARMFUL, 0xe6ccc4)
	);
	public static final String MOD_ID = "taccorpstrinkets";
	public static final Logger LOGGER = LoggerFactory.getLogger("Taccorp's Trinkets");
	public static final Identifier TAC_BOOPED_ID = new Identifier("taccorpstrinkets:tacplush_booped");
	public static SoundEvent TAC_BOOPED_SOUND_EVENT = new SoundEvent(TAC_BOOPED_ID);

	public static final Identifier HAMMER_SLAM_ID = new Identifier("taccorpstrinkets:hammer_slammed");
	public static SoundEvent HAMMER_SLAMMED = new SoundEvent(HAMMER_SLAM_ID);
	public static final Identifier WEAK_HAMMER_SLAM_ID = new Identifier("taccorpstrinkets:weak_hammer_slammed");
	public static SoundEvent WEAK_HAMMER_SLAMMED = new SoundEvent(WEAK_HAMMER_SLAM_ID);
	public static final Identifier HAMMER_THROW_ID = new Identifier("taccorpstrinkets:hammer_throw");
	public static SoundEvent HAMMER_THROW = new SoundEvent(HAMMER_THROW_ID);
	public static final Identifier HAMMER_SHOCKWAVE_ID = new Identifier("taccorpstrinkets:hammer_shockwave");
	public static SoundEvent HAMMER_SHOCKWAVE = new SoundEvent(HAMMER_SHOCKWAVE_ID);
	public static final Enchantment STUNNING = new StunningEnchantment(Enchantment.Rarity.RARE, EnchantmentTarget.DIGGER, EquipmentSlot.MAINHAND);
	public static final Enchantment FLINGING = new FlingingEnchantment(Enchantment.Rarity.RARE, EnchantmentTarget.DIGGER, EquipmentSlot.MAINHAND);
	public static final Enchantment SLAMMING = new SlammingEnchantment(Enchantment.Rarity.RARE, EnchantmentTarget.DIGGER, EquipmentSlot.MAINHAND);
	public static final DefaultParticleType HAMMER_SLAM = FabricParticleTypes.simple();
	public static final DefaultParticleType HAMMER_WAVE = FabricParticleTypes.simple();

	@Override
	public void onInitialize(ModContainer mod) {
		TacBlocks.registerModBlocks();
		TacItems.registerModItems();
		TacWorldGen.generateGeode();
		TacConfigFeatures.registerConfiguredFeatures();
		Registry.register(Registry.SOUND_EVENT, TAC_BOOPED_ID, TAC_BOOPED_SOUND_EVENT);
		Registry.register(Registry.SOUND_EVENT, HAMMER_SLAM_ID, HAMMER_SLAMMED);
		Registry.register(Registry.SOUND_EVENT, WEAK_HAMMER_SLAM_ID, WEAK_HAMMER_SLAMMED);
		Registry.register(Registry.SOUND_EVENT, HAMMER_THROW_ID, HAMMER_THROW);
		Registry.register(Registry.SOUND_EVENT, HAMMER_SHOCKWAVE_ID, HAMMER_SHOCKWAVE);
		Registry.register(Registry.ENCHANTMENT, new Identifier("taccorpstrinkets", "stunning"), STUNNING);
		Registry.register(Registry.ENCHANTMENT, new Identifier("taccorpstrinkets", "flinging"), FLINGING);
		Registry.register(Registry.ENCHANTMENT, new Identifier("taccorpstrinkets", "slamming"), SLAMMING);

		Registry.register(Registry.PARTICLE_TYPE, new Identifier("taccorpstrinkets", "hammer_slam"), HAMMER_SLAM);
		Registry.register(Registry.PARTICLE_TYPE, new Identifier("taccorpstrinkets", "hammer_wave"), HAMMER_WAVE);
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
