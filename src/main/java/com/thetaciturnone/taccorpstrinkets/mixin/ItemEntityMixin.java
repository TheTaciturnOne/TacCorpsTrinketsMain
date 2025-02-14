package com.thetaciturnone.taccorpstrinkets.mixin;

import com.thetaciturnone.taccorpstrinkets.registries.TacItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {
	@Shadow
	public abstract ItemStack getStack();

	public ItemEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@Override
	protected void onBlockCollision(BlockState state) {
		if (state.isOf(Blocks.LAVA) && getStack().getItem().equals(Items.NETHERITE_INGOT)) {
			BlockPos magmaPos = getBlockPos().down(1);
			BlockState magmaState = world.getBlockState(magmaPos);
			if (magmaState.isOf(Blocks.MAGMA_BLOCK)) {
				dropStack(new ItemStack(TacItems.NETHERITE_SLAG, 1));
				this.playSound(SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, 10.0F, 1.0F);
				this.discard();
			}
		}
	}

}
