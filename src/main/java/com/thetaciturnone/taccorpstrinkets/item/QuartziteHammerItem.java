package com.thetaciturnone.taccorpstrinkets.item;

import com.thetaciturnone.taccorpstrinkets.TacCorpsTrinkets;
import com.thetaciturnone.taccorpstrinkets.entity.ThrownHammerEntity;
import com.thetaciturnone.taccorpstrinkets.utils.TacDamage;
import com.thetaciturnone.taccorpstrinkets.utils.TaccorpsUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class QuartziteHammerItem extends PickaxeItem {
    public QuartziteHammerItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }
    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.damageShield(3);
		spawnHammerSlamParticles(target);
		playHammerSlamSound(target);
		return super.postHit(stack, target, attacker);
    }

	public void spawnHammerSlamParticles(LivingEntity player) {
		double deltaX = -MathHelper.sin((float) (double) player.getYaw() * 0.017453292F);
		double deltaZ = MathHelper.cos((float) (double) player.getYaw() * 0.017453292F);
		if (player.world instanceof ServerWorld) {
			((ServerWorld)player.world).spawnParticles(TacCorpsTrinkets.HAMMER_SLAM, player.getX() + deltaX, player.getBodyY(0.5), player.getZ() + deltaZ, 0, deltaX, 0.0, deltaZ, 0.0);
		}
	}

	public void playHammerSlamSound(LivingEntity user) {
		if (user.world instanceof ServerWorld) {
			user.world.playSound(null, user.getX(), user.getY(), user.getZ(), TacCorpsTrinkets.HAMMER_SLAMMED, SoundCategory.NEUTRAL, 1.0F, 1.0F);
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
		} else if (TaccorpsUtil.hasEnchantment(TacCorpsTrinkets.SLAMMING, user)) {
			user.setCurrentHand(hand);
			return TypedActionResult.consume(itemStack);
		}
		else return TypedActionResult.fail(itemStack);
    }

	public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
		if (user instanceof PlayerEntity playerEntity) {
			if (TaccorpsUtil.hasEnchantment(TacCorpsTrinkets.FLINGING, user)) {
				int i = this.getMaxUseTime(stack) - remainingUseTicks;
				if (i >= 8) {
					if (!world.isClient) {
						stack.damage(1, playerEntity, (p) -> {
							p.sendToolBreakStatus(user.getActiveHand());
						});
						ThrownHammerEntity tridentEntity = new ThrownHammerEntity(world, playerEntity, stack);
						tridentEntity.setProperties(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0F, 2.5F * 0.5F, 1.0F);
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
			} else if (TaccorpsUtil.hasEnchantment(TacCorpsTrinkets.SLAMMING, user)) {
				int i = this.getMaxUseTime(stack) - remainingUseTicks;
				if (i >= 4) {
					((PlayerEntity) user).getItemCooldownManager().set(this, 100);
					world.getOtherEntities(user, user.getBoundingBox().expand(4f, 2f, 4f)).forEach(e -> {
						if (e instanceof LivingEntity living) {
							living.takeKnockback(1, user.getX() - living.getX(), user.getZ() - living.getZ());
						}
					});
					world.getOtherEntities(user, user.getBoundingBox().expand(4f, 2f, 4f).offset(0f, -0.5f, 0f)).forEach(e ->
						e.damage(TacDamage.HAMMER_SHOCKWAVE, 4));
					world.playSound(null, user.getX(), user.getY(), user.getZ(), TacCorpsTrinkets.HAMMER_SHOCKWAVE, SoundCategory.NEUTRAL, 2.0f, 1.0f);
					world.addParticle(TacCorpsTrinkets.HAMMER_WAVE, user.getX(), user.getY() + 0.1, user.getZ(), 0, 0, 0);
				}
			}
		}
	}

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(Text.literal("Tac's trademark weapon.").formatted(Formatting.DARK_AQUA));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
