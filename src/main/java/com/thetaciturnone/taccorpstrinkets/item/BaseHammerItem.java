package com.thetaciturnone.taccorpstrinkets.item;

import com.thetaciturnone.taccorpstrinkets.TacCorpsTrinkets;
import com.thetaciturnone.taccorpstrinkets.component.HammerUserComponent;
import com.thetaciturnone.taccorpstrinkets.entity.ThrownHammerEntity;
import com.thetaciturnone.taccorpstrinkets.registries.*;
import com.thetaciturnone.taccorpstrinkets.utils.TacDamage;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.Tameable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

import java.util.List;
import java.util.Objects;

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
			HammerVariantComponent hammerVariantComponent = user.getMainHandStack().get(TacItemComponents.HAMMER_VARIANT);
			SoundEvent sound = (hammerVariantComponent != null && hammerVariantComponent.variant() == 4) ? SoundEvents.BLOCK_BELL_USE : TacCorpsTrinkets.HAMMER_SLAMMED;
			serverWorld.playSound(null, user.getX(), user.getY(), user.getZ(), sound, SoundCategory.NEUTRAL, 1.0F, 1.0F);
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
		if (itemStack.getDamage() >= itemStack.getMaxDamage() - 1 || user.hasStatusEffect(TacCorpsTrinkets.STUNNED)) {
			return TypedActionResult.fail(itemStack);
		} else if (EnchantmentHelper.hasAnyEnchantmentsWith(itemStack, TacEnchantmentEffects.HAMMER_THROW)
			|| EnchantmentHelper.hasAnyEnchantmentsWith(itemStack, TacEnchantmentEffects.VAULT)) {
			user.setCurrentHand(hand);
			return TypedActionResult.consume(itemStack);
		}
		else return super.use(world, user, hand);
	}

	@Override
	public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
		if (user.hasStatusEffect(TacCorpsTrinkets.STUNNED)) {
			user.stopUsingItem();
		}
		super.usageTick(world, user, stack, remainingUseTicks);
	}

	public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
		if (!user.hasStatusEffect(TacCorpsTrinkets.STUNNED)) {
			if (user instanceof PlayerEntity playerEntity) {
				int useTime = this.getMaxUseTime(stack, user) - remainingUseTicks;
				if (EnchantmentHelper.hasAnyEnchantmentsWith(stack, TacEnchantmentEffects.HAMMER_THROW)) {
					if (useTime >= 12 && stack.getDamage() < stack.getMaxDamage()) {
						if (!world.isClient()) {
							this.setCooldowns(playerEntity, 45);
							stack.damage(1, playerEntity, LivingEntity.getSlotForHand(user.getActiveHand()));
							ThrownHammerEntity thrownHammerEntity = new ThrownHammerEntity(world, playerEntity, stack);
							thrownHammerEntity.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0F, 2.5F * 0.5F, 1.0F);
							if (playerEntity.isCreative()) {
								thrownHammerEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
							} else { // if player not in creative
								playerEntity.getInventory().removeOne(stack);
							}
							world.spawnEntity(thrownHammerEntity);
							world.playSoundFromEntity(null, thrownHammerEntity, TacCorpsTrinkets.HAMMER_THROW, SoundCategory.PLAYERS, 1.0F, 1.0F);
						}
					}
				}
				else if (EnchantmentHelper.hasAnyEnchantmentsWith(stack, TacEnchantmentEffects.VAULT)) {
					if (useTime >= 12) {
						HammerUserComponent hammerUserComponent = TacEntityComponents.HAMMER_USER.get(playerEntity);
						if (!hammerUserComponent.hasBoosted()) {
							hammerUserComponent.setBoosted(true);
							this.setCooldowns(playerEntity, 30);
							float yaw = playerEntity.getYaw();
							float pitch = playerEntity.getPitch();
							float h = -MathHelper.sin(yaw * 0.0175f) * MathHelper.cos(pitch * 0.0175f);
							float k = -MathHelper.sin(pitch * 0.0175f);
							float l = MathHelper.cos(yaw * 0.0175f) * MathHelper.cos(pitch * 0.0175f);
							float m = MathHelper.sqrt(h * h + k * k + l * l);
							float n = 2.0F * (3.5f / 4.0F);
							h *= n / m;
							k *= n / m;
							l *= n / m;
							playerEntity.addVelocity(h, k, l);
							if (playerEntity.isOnGround()) {
								playerEntity.move(MovementType.SELF, new Vec3d(0.0, 1.2, 0.0));
							}

							world.playSoundFromEntity(null, playerEntity, TacCorpsTrinkets.HAMMER_WHIRRING, SoundCategory.PLAYERS, 1.0F, 1.0F);
						}
						else {
							this.setCooldowns(playerEntity, 40);
							hammerUserComponent.setBoostedSquared(true);
							world.addParticle(TacCorpsTrinkets.HAMMER_WAVE, user.getX(), user.getY() + 0.2, user.getZ(), 0, 0, 0);
							playerEntity.addVelocity(0, -1.0, 0);
							user.velocityModified = true;
						}
					}
				}
			}
		}
	}

	public static boolean shockwaveShouldDamage(LivingEntity entity, LivingEntity user) {
		return entity != user && entity.isAttackable() && !entity.isTeammate(user) && !(entity instanceof Tameable tameable && tameable.getOwner() == user);
	}

	private void setCooldowns(PlayerEntity playerEntity, int baseCooldown) {
		playerEntity.getItemCooldownManager().set(TacItems.QUARTZITE_HAMMER, baseCooldown / 2);
		playerEntity.getItemCooldownManager().set(TacItems.SHATTERED_QUARTZITE_HAMMER, baseCooldown);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		BlockPos blockPos = context.getBlockPos();
		ItemStack stack = context.getStack();
		PlayerEntity playerEntity = context.getPlayer();
		if (world.getBlockState(blockPos).isOf(TacBlocks.QUARTZ_CRYSTAL_CLUSTER) && Objects.requireNonNull(playerEntity).isSneaking()) {
			ItemStack repairedHammer = new ItemStack(TacItems.QUARTZITE_HAMMER);
			if (stack.getComponents() != null) { // repairing a hammer will carry over item data like name, durability, and enchantments
				repairedHammer.applyChanges(stack.getComponentChanges()); // preserves the repaired hammer's default components like damage attributes :p
			}
			playerEntity.setStackInHand(playerEntity.getActiveHand(), repairedHammer);
			world.playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), TacCorpsTrinkets.HAMMER_SHATTER, SoundCategory.NEUTRAL, 1.0F, 1.0F);
			world.breakBlock(blockPos, false);
			return ActionResult.success(world.isClient());
		}
		return super.useOnBlock(context);
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
