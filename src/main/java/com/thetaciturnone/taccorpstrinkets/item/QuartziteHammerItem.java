package com.thetaciturnone.taccorpstrinkets.item;

import com.thetaciturnone.taccorpstrinkets.TacCorpsTrinkets;
import com.thetaciturnone.taccorpstrinkets.entity.ThrownHammerEntity;
import com.thetaciturnone.taccorpstrinkets.registries.TacBlocks;
import com.thetaciturnone.taccorpstrinkets.registries.TacItems;
import com.thetaciturnone.taccorpstrinkets.utils.TacDamage;
import com.thetaciturnone.taccorpstrinkets.utils.TaccorpsUtil;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.Tameable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.*;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class QuartziteHammerItem extends PickaxeItem {
    public QuartziteHammerItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

	public static int getStyle(ItemStack stack) {
		if (stack.getNbt() == null) {
			return 0;
		}
		return stack.getNbt().getInt("style");
	}

	public static void setStyle(ItemStack stack, int style) {
		stack.getOrCreateNbt().putInt("style", style);
	}

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.damageShield(3);
		spawnHammerSlamParticles(target);
		if (attacker.fallDistance > 1.5F && !attacker.isFallFlying() && TaccorpsUtil.hasEnchantment(TacCorpsTrinkets.SLAMMING, attacker)) {
			float h = attacker.fallDistance;
			float i;
			if (h <= 5.0f){
				i = h + 1;
			} else if (h <= 10.0f){
				i = h + 2;
			}
			else if (h < 25.0f){
				i = h + 4;
			}
			else if (h >= 25.0f){
				i = h + 5;
			} else i = h;
			target.damage(TacDamage.create(attacker.getWorld(), TacDamage.HAMMER_POWERSLAM, attacker), i);
			target.getWorld().playSound(null, target.getX(), target.getY(), target.getZ(), TacCorpsTrinkets.HAMMER_POWERSLAM, SoundCategory.NEUTRAL, 2.0f, 1.0f);
			spawnHammerWaveParticle(target);
			attacker.addVelocity(0, 1.2, 0);
			attacker.velocityModified = true;
		}
		playHammerSlamSound(target);
		return super.postHit(stack, target, attacker);
    }

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		BlockPos blockPos = context.getBlockPos();
		BlockState blockState = world.getBlockState(blockPos);
		PlayerEntity playerEntity = context.getPlayer();
		if (blockState.isIn(BlockTags.ANVIL) && Objects.requireNonNull(playerEntity).isSneaking()) {
			ItemStack shatteredHammer = new ItemStack(TacItems.SHATTERED_QUARTZITE_HAMMER);
			if (context.getStack().getNbt() != null) { // shattering a hammer will carry over item data like name, durability, and enchantments
				shatteredHammer.getOrCreateNbt().copyFrom(context.getStack().getNbt());
			}
			playerEntity.setStackInHand(playerEntity.getActiveHand(), shatteredHammer);
			world.playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), TacCorpsTrinkets.HAMMER_SHATTER, SoundCategory.NEUTRAL, 1.0F, 1.0F);
			return ActionResult.success(world.isClient());
		} else return super.useOnBlock(context);
	}

	public void spawnHammerSlamParticles(LivingEntity player) {
		double deltaX = -MathHelper.sin((float) (double) player.getYaw() * 0.017453292F);
		double deltaZ = MathHelper.cos((float) (double) player.getYaw() * 0.017453292F);
		if (player.getWorld() instanceof ServerWorld serverWorld) {
			serverWorld.spawnParticles(TacCorpsTrinkets.HAMMER_SLAM, player.getX() + deltaX, player.getBodyY(0.5), player.getZ() + deltaZ, 0, deltaX, 0.0, deltaZ, 0.0);
		}
	}
	public void spawnHammerWaveParticle(LivingEntity player) {
		if (player.getWorld() instanceof ServerWorld serverWorld) {
			serverWorld.spawnParticles(TacCorpsTrinkets.HAMMER_WAVE, player.getX(), player.getBodyY(0.5), player.getZ() , 1, 0, 0.0, 0, 0.0);
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

	public int getMaxUseTime(ItemStack stack) {
		return 72000;
	}

	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		if (itemStack.getDamage() >= itemStack.getMaxDamage() - 1) {
			return TypedActionResult.fail(itemStack);
		} else if (TaccorpsUtil.hasEnchantment(TacCorpsTrinkets.FLINGING, user)) {
			user.setCurrentHand(hand);
			return TypedActionResult.consume(itemStack);
		} else if (TaccorpsUtil.hasEnchantment(TacCorpsTrinkets.VAULTING, user)) {
			user.setCurrentHand(hand);
			return TypedActionResult.consume(itemStack);
		} else if (TaccorpsUtil.hasEnchantment(TacCorpsTrinkets.BOOSTING, user)) {
			user.setCurrentHand(hand);
			return TypedActionResult.consume(itemStack);
		}
		else return super.use(world, user, hand);
    }

	public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
		if (user instanceof PlayerEntity playerEntity) {
			if (TaccorpsUtil.hasEnchantment(TacCorpsTrinkets.FLINGING, user)) {
				int i = this.getMaxUseTime(stack) - remainingUseTicks;
				if (i >= 6) {
					if (!world.isClient()) {
						stack.damage(1, playerEntity, (p) -> {
							p.sendToolBreakStatus(user.getActiveHand());
						});
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
			} else if (TaccorpsUtil.hasEnchantment(TacCorpsTrinkets.VAULTING, user)) {
				int i = this.getMaxUseTime(stack) - remainingUseTicks;
				if (i >= 4) {
					playerEntity.getItemCooldownManager().set(this, 70);
					playerEntity.getItemCooldownManager().set(TacItems.SHATTERED_QUARTZITE_HAMMER, 105);
					for (LivingEntity entity : world.getNonSpectatingEntities(LivingEntity.class, user.getBoundingBox().expand(4f, 2f, 4f))) {
						if (shockwaveShouldDamage(entity, user)) {
							entity.takeKnockback(1, user.getX() - entity.getX(), user.getZ() - entity.getZ());
							entity.damage(TacDamage.create(user.getWorld(), TacDamage.HAMMER_POWERSLAM, user), 8);
						}
					}
					if(!world.isClient())
					{
						for (int y = 0; y <= 3; y++)
						{
							for (int x = -3; x <= 3; x++)
							{
								for (int z = -3; z <= 3; z++)
								{
									BlockPos pos = playerEntity.getBlockPos().add(new Vec3i(x, y, z));
									if(world.getBlockState(pos).isOf(Blocks.GLASS_PANE)) {
										world.breakBlock(pos, false, user);
									}
									if(world.getBlockState(pos).isOf(Blocks.GLASS)) {
										world.breakBlock(pos, false, user);
									}
									if(world.getBlockState(pos).isOf(TacBlocks.QUARTZ_GLASS)) {
										world.breakBlock(pos, false, user);
									}
									if(world.getBlockState(pos).isOf(TacBlocks.QUARTZ_GLASS_PANE)) {
										world.breakBlock(pos, false, user);
									}

								}
							}
						}
					}
					world.playSound(null, user.getX(), user.getY(), user.getZ(), TacCorpsTrinkets.HAMMER_SHOCKWAVE, SoundCategory.NEUTRAL, 2.0f, 1.0f);
					world.addParticle(TacCorpsTrinkets.HAMMER_WAVE, user.getX(), user.getY() + 0.2, user.getZ(), 0, 0, 0);
					playerEntity.addVelocity(0, 1, 0);
					user.velocityModified = true;
				}
			}
			else if (TaccorpsUtil.hasEnchantment(TacCorpsTrinkets.BOOSTING, user)) {
				int i = this.getMaxUseTime(stack) - remainingUseTicks;
				if (i >= 4) {
					playerEntity.getItemCooldownManager().set(this, 40);
					playerEntity.getItemCooldownManager().set(TacItems.SHATTERED_QUARTZITE_HAMMER, 60);
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
					playerEntity.useRiptide(20);
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
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(Text.translatable("item.taccorpstrinkets.quartzite_hammer.tooltip").setStyle(Style.EMPTY.withColor(0xd0c6b6)));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
