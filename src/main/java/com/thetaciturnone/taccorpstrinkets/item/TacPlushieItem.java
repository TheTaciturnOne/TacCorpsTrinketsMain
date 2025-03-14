package com.thetaciturnone.taccorpstrinkets.item;

import com.thetaciturnone.taccorpstrinkets.entity.ThrownTacEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ProjectileItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;

public class TacPlushieItem extends BlockItem implements ProjectileItem {

	public TacPlushieItem(Block block, Item.Settings settings) {
		super(block, settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
			world.playSound(
				null,
				user.getX(),
				user.getY(),
				user.getZ(),
				SoundEvents.ENTITY_ARROW_SHOOT,
				SoundCategory.NEUTRAL,
				0.5F,
				1.5F / (world.getRandom().nextFloat() * 0.4F + 0.8F)
			);
			if (!world.isClient) {
				ThrownTacEntity tac = new ThrownTacEntity(world, user);
				tac.setItem(itemStack);
				tac.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);
				world.spawnEntity(tac);
			}

			user.incrementStat(Stats.USED.getOrCreateStat(this));
			if (!user.isCreative()) {
				itemStack.decrement(1);
			}
		return TypedActionResult.success(itemStack, world.isClient());
	}

	@Override
	public ProjectileEntity createEntity(World world, Position pos, ItemStack stack, Direction direction) {
		ThrownTacEntity tac = new ThrownTacEntity(world, pos.getX(), pos.getY(), pos.getZ());
		tac.setItem(stack);
		return tac;
	}
}
