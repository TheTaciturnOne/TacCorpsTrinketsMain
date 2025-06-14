package com.thetaciturnone.taccorpstrinkets.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.thetaciturnone.taccorpstrinkets.TacCorpsTrinkets;
import com.thetaciturnone.taccorpstrinkets.component.HammerUserComponent;
import com.thetaciturnone.taccorpstrinkets.item.QuartziteHammerItem;
import com.thetaciturnone.taccorpstrinkets.item.BaseHammerItem;
import com.thetaciturnone.taccorpstrinkets.registries.TacEnchantmentEffects;
import com.thetaciturnone.taccorpstrinkets.registries.TacEntityComponents;
import com.thetaciturnone.taccorpstrinkets.utils.TacDamage;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	public LivingEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@Shadow
	public abstract ItemStack getMainHandStack();

	@Shadow
	public abstract ItemStack getActiveItem();

	@Shadow
	public abstract boolean addStatusEffect(StatusEffectInstance effect);

	@ModifyReturnValue(method = "disablesShield", at = @At("RETURN"))
    public boolean tacCorp$hammerDisablesShield(boolean original) {
        if (this.getMainHandStack().getItem() instanceof QuartziteHammerItem || this.getMainHandStack().getItem() instanceof BaseHammerItem) {
            return true;
        }
		return original;
	}

	@ModifyReturnValue(method = "damage", at = @At("TAIL"))
	private boolean tacCorp$damageDuringCharge(boolean original, DamageSource source, float amount) {
		if (original && EnchantmentHelper.hasAnyEnchantmentsWith(this.getActiveItem(), TacEnchantmentEffects.HAMMER_THROW)) {
			this.addStatusEffect(new StatusEffectInstance(TacCorpsTrinkets.STUNNED, 30, 0, false, false, true));
		}
		return original;
	}

	@WrapMethod(method = "handleFallDamage")
	private boolean tacCorp$reducedVaultingFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource, Operation<Boolean> original) {
		if (TacEntityComponents.HAMMER_USER.maybeGet(this).isPresent()) {
			HammerUserComponent hammerUserComponent = TacEntityComponents.HAMMER_USER.maybeGet(this).get();
			if (hammerUserComponent.hasBoosted()) {
				damageMultiplier *= 0.35f;
			}
		}
		return original.call(fallDistance, damageMultiplier, damageSource);
	}

	@Override
	public void onLanding() {
		super.onLanding();
		var self = (LivingEntity) (Object) (this);
		if (getLandingBlockState().isSolid() && self instanceof PlayerEntity player) {
			HammerUserComponent hammerUserComponent = TacEntityComponents.HAMMER_USER.get(player);
			if (hammerUserComponent.hasBoostedSquared()) {
				for (LivingEntity entity : getWorld().getNonSpectatingEntities(LivingEntity.class, self.getBoundingBox().expand(4f, 2f, 4f))) {
					if (BaseHammerItem.shockwaveShouldDamage(entity, player)) {
						entity.takeKnockback(1, player.getX() - entity.getX(), player.getZ() - entity.getZ());
						entity.damage(TacDamage.create(getWorld(), TacDamage.HAMMER_SHOCKWAVE, player), entity instanceof PlayerEntity ? 8 : 24);
					}
				}
				if (!getWorld().isClient()) {
					var pos = new BlockPos.Mutable();

					for (int y = 0; y <= 3; ++y) {
						for (int x = -3; x <= 3; ++x) {
							for (int z = -3; z <= 3; ++z) {
								pos.set(player.getBlockPos())
									.move(x, y, z);
								if (getWorld() instanceof ServerWorld world) {
									world.spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, world.getBlockState(pos)),
										pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 25, 0.5F, 0.5F, 0.5F, 0.5F);
								}
								if (getWorld().getBlockState(pos).isIn(TacCorpsTrinkets.BREAKABLE_GLASS_TAG)) {
									getWorld().breakBlock(pos, false, player);
								}
							}
						}
					}
				}
				hammerUserComponent.setBoostedSquared(false);
				getWorld().playSound(null, player.getX(), player.getY(), player.getZ(), TacCorpsTrinkets.HAMMER_CRASH, SoundCategory.NEUTRAL, 3.5f, 1.0f);
				getWorld().addParticle(TacCorpsTrinkets.HAMMER_WAVE, player.getX(), player.getY() + 0.2, player.getZ(), 0, 0, 0);
			}
		}
	}
}
