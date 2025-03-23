package com.thetaciturnone.taccorpstrinkets.item;

import com.thetaciturnone.taccorpstrinkets.TacCorpsTrinkets;
import com.thetaciturnone.taccorpstrinkets.entity.ThrownHammerEntity;
import com.thetaciturnone.taccorpstrinkets.registries.TacEnchantmentEffects;
import com.thetaciturnone.taccorpstrinkets.registries.TacItems;
import com.thetaciturnone.taccorpstrinkets.utils.TacDamage;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.Tameable;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

import java.util.List;

public class BaseHammerItem extends PickaxeItem implements ProjectileItem {
    public BaseHammerItem(ToolMaterial toolMaterial, Item.Settings settings) {
        super(toolMaterial, settings);
    }

	@Override
	public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		target.damageShield(3);
		spawnHammerSlamParticles(target);
		playHammerSlamSound(target);
		super.postDamageEntity(stack, target, attacker);
	}

	@Override
	public float getBonusAttackDamage(Entity target, float baseAttackDamage, DamageSource damageSource) {
		ItemStack stack = damageSource.getWeaponStack();
		if (damageSource.getAttacker() instanceof LivingEntity attacker && stack != null) {
			if (attacker.fallDistance > 1.5F && !attacker.isFallFlying() && EnchantmentHelper.hasAnyEnchantmentsWith(stack, EnchantmentEffectComponentTypes.SMASH_DAMAGE_PER_FALLEN_BLOCK)) {
				float h = attacker.fallDistance;
				float damage;
				if (h <= 2.0f) {
					damage = (h / 5) + 1;
				} else if (h <= 10.0f) {
					damage = (h / 5) + 2;
				} else if (h < 25.0f) {
					damage =  (h / 5) + 4;
				} else if (h >= 25.0f) {
					damage = Math.min((h / 5) + 5, 35); // new formula yayyyyy
				} else damage = h;
				target.getWorld().playSound(null, target.getX(), target.getY(), target.getZ(), TacCorpsTrinkets.HAMMER_POWERSLAM, SoundCategory.NEUTRAL, 2.0f, 1.0f);
				spawnHammerWaveParticle(target);
				attacker.addVelocity(0, 1.2, 0);
				attacker.velocityModified = true;
				return damage;
			}
		}
		return super.getBonusAttackDamage(target, baseAttackDamage, damageSource);
	}

	public void spawnHammerWaveParticle(Entity entity) {
		if (entity.getWorld() instanceof ServerWorld serverWorld) {
			serverWorld.spawnParticles(TacCorpsTrinkets.HAMMER_WAVE, entity.getX(), entity.getBodyY(0.5), entity.getZ() , 1, 0, 0.0, 0, 0.0);
		}
	}

	public void spawnHammerSlamParticles(LivingEntity player) {
		double deltaX = -MathHelper.sin((float) (double) player.getYaw() * 0.017453292F);
		double deltaZ = MathHelper.cos((float) (double) player.getYaw() * 0.017453292F);
		if (player.getWorld() instanceof ServerWorld serverWorld) {
			serverWorld.spawnParticles(TacCorpsTrinkets.HAMMER_SLAM, player.getX() + deltaX, player.getBodyY(0.5), player.getZ() + deltaZ, 0, deltaX, 0.0, deltaZ, 0.0);
		}
	}

	public void playHammerSlamSound(LivingEntity user) {
		if (user.getWorld() instanceof ServerWorld serverWorld) {
			serverWorld.playSound(null, user.getX(), user.getY(), user.getZ(), TacCorpsTrinkets.HAMMER_SLAMMED, SoundCategory.NEUTRAL, 1.0F, 1.0F);
		}
	}

	public UseAction getUseAction(ItemStack stack) {
		return UseAction.SPEAR;
	}

	public int getMaxUseTime(ItemStack stack, LivingEntity user) {
		return 72000;
	}

	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		if (itemStack.getDamage() >= itemStack.getMaxDamage() - 1) {
			return TypedActionResult.fail(itemStack);
		} else if (EnchantmentHelper.hasAnyEnchantmentsWith(itemStack, EnchantmentEffectComponentTypes.TRIDENT_SPIN_ATTACK_STRENGTH)
			|| EnchantmentHelper.hasAnyEnchantmentsWith(itemStack, TacEnchantmentEffects.THROWABLE)
			|| EnchantmentHelper.hasAnyEnchantmentsWith(itemStack, TacEnchantmentEffects.VAULT)) {
			user.setCurrentHand(hand);
			return TypedActionResult.consume(itemStack);
		}
		else return super.use(world, user, hand);
	}

	public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
		if (user instanceof PlayerEntity playerEntity) {
			if (EnchantmentHelper.hasAnyEnchantmentsWith(stack, TacEnchantmentEffects.THROWABLE)) {
				int i = this.getMaxUseTime(stack, user) - remainingUseTicks;
				if (i >= 8 && stack.getDamage() < stack.getMaxDamage()) {
					if (!world.isClient()) {
						stack.damage(1, playerEntity, LivingEntity.getSlotForHand(user.getActiveHand()));
						ThrownHammerEntity tridentEntity = new ThrownHammerEntity(world, playerEntity, stack);
						tridentEntity.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0F, 2.5F * 0.5F, 1.0F);
						if (playerEntity.getAbilities().creativeMode) {
							tridentEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
						}

						world.spawnEntity(tridentEntity);
						world.playSoundFromEntity(null, tridentEntity, TacCorpsTrinkets.HAMMER_THROW, SoundCategory.PLAYERS, 1.0F, 1.0F);
						if (!playerEntity.getAbilities().creativeMode) {
							playerEntity.getInventory().removeOne(stack);
						}
					}
				}
			} else if (EnchantmentHelper.hasAnyEnchantmentsWith(stack, TacEnchantmentEffects.VAULT)) {
				int i = this.getMaxUseTime(stack, user) - remainingUseTicks;
				if (i >= 4) {
					playerEntity.getItemCooldownManager().set(this, 30);
					for (LivingEntity entity : world.getNonSpectatingEntities(LivingEntity.class, user.getBoundingBox().expand(4f, 2f, 4f))) {
						if (QuartziteHammerItem.shockwaveShouldDamage(entity, user)) {
							entity.takeKnockback(1, user.getX() - entity.getX(), user.getZ() - entity.getZ());
							entity.damage(TacDamage.create(user.getWorld(), TacDamage.HAMMER_SHOCKWAVE, user), entity instanceof PlayerEntity ? 8 : 24);
						}
					}
					if(!world.isClient()) {
						var pos = new BlockPos.Mutable();

						for (int y = 0; y <= 3; ++y) {
							for (int x = -3; x <= 3; ++x) {
								for (int z = -3; z <= 3; ++z) {
									pos.set(playerEntity.getBlockPos())
										.move(x, y, z);

									if (world.getBlockState(pos).isIn(TacCorpsTrinkets.BREAKABLE_GLASS_TAG)) {
										world.breakBlock(pos, false, user);
									}
								}
							}
						}
					}
					world.playSound(null, user.getX(), user.getY(), user.getZ(), TacCorpsTrinkets.HAMMER_SHOCKWAVE, SoundCategory.NEUTRAL, 2.0f, 1.0f);
					world.addParticle(TacCorpsTrinkets.HAMMER_WAVE, user.getX(), user.getY() + 0.2, user.getZ(), 0, 0, 0);
					playerEntity.addVelocity(0, playerEntity.isOnGround() ? 1.5 : 1.0, 0); // gives increased velocity when on the ground
					user.velocityModified = true;
				}
			}
			else if (EnchantmentHelper.hasAnyEnchantmentsWith(stack, EnchantmentEffectComponentTypes.TRIDENT_SPIN_ATTACK_STRENGTH)) {
				int i = this.getMaxUseTime(stack, user) - remainingUseTicks;
				if (i >= 4) {
					playerEntity.getItemCooldownManager().set(this, 60);
					playerEntity.getItemCooldownManager().set(TacItems.QUARTZITE_HAMMER, 40);
					float f = playerEntity.getYaw();
					float g = playerEntity.getPitch();
					float h = -MathHelper.sin(f * 0.017453292F) * MathHelper.cos(g * 0.017453292F);
					float k = -MathHelper.sin(g * 0.017453292F);
					float l = MathHelper.cos(f * 0.017453292F) * MathHelper.cos(g * 0.017453292F);
					float m = MathHelper.sqrt(h * h + k * k + l * l);
					float n = 3.0F * ((1.0F + (float)2.5) / 4.0F);
					h *= n / m;
					k *= n / m;
					l *= n / m;
					playerEntity.addVelocity(h, k, l);
					playerEntity.useRiptide(20, 10.0f, stack);
					if (playerEntity.isOnGround()) {
						playerEntity.move(MovementType.SELF, new Vec3d(0.0, 1.1999999284744263, 0.0));
					}

					world.playSoundFromEntity(null, playerEntity, TacCorpsTrinkets.HAMMER_WHIRRING, SoundCategory.PLAYERS, 1.0F, 1.0F);
				}
			}
		}
	}

	public static boolean shockwaveShouldDamage(LivingEntity entity, LivingEntity user) {
		return entity != user && entity.isAttackable() && !entity.isTeammate(user) && !(entity instanceof Tameable tameable && tameable.getOwner() == user);
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		tooltip.add(Text.translatable(this.getTranslationKey(stack) + ".tooltip").setStyle(Style.EMPTY.withColor(0xd0c6b6)));
		super.appendTooltip(stack, context, tooltip, type);
	}

	@Override
	public ProjectileEntity createEntity(World world, Position pos, ItemStack stack, Direction direction) {
		ThrownHammerEntity hammerEntity = new ThrownHammerEntity(world, pos.getX(), pos.getY(), pos.getZ(), stack.copyWithCount(1));
		hammerEntity.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
		return hammerEntity;
	}
}
