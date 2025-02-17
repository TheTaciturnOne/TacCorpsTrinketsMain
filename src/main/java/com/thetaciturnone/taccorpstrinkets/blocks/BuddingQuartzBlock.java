package com.thetaciturnone.taccorpstrinkets.blocks;

import com.thetaciturnone.taccorpstrinkets.registries.TacBlocks;
import net.minecraft.block.*;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;

public class BuddingQuartzBlock extends QuartzCrystalBlock {
	public static final int GROW_CHANCE = 5;
	private static final Direction[] DIRECTIONS = Direction.values();

	public BuddingQuartzBlock(Settings settings) {
		super(settings);
	}

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (random.nextInt(GROW_CHANCE) == 0) {
			Direction direction = DIRECTIONS[random.nextInt(DIRECTIONS.length)];
			BlockPos blockPos = pos.offset(direction);
			BlockState blockState = world.getBlockState(blockPos);
			Block block = null;
			if (canGrowIn(blockState)) {
				block = TacBlocks.SMALL_QUARTZ_CRYSTAL_CLUSTER;
			} else if (blockState.isOf(TacBlocks.SMALL_QUARTZ_CRYSTAL_CLUSTER) && blockState.get(AmethystClusterBlock.FACING) == direction) {
				block = TacBlocks.MEDIUM_QUARTZ_CRYSTAL_CLUSTER;
			} else if (blockState.isOf(TacBlocks.MEDIUM_QUARTZ_CRYSTAL_CLUSTER) && blockState.get(AmethystClusterBlock.FACING) == direction) {
				block = TacBlocks.LARGE_QUARTZ_CRYSTAL_CLUSTER;
			} else if (blockState.isOf(TacBlocks.LARGE_QUARTZ_CRYSTAL_CLUSTER) && blockState.get(AmethystClusterBlock.FACING) == direction) {
				block = TacBlocks.QUARTZ_CRYSTAL_CLUSTER;
			}

			if (block != null) {
				BlockState blockState2 = block.getDefaultState().with(AmethystClusterBlock.FACING, direction).with(AmethystClusterBlock.WATERLOGGED, blockState.getFluidState().getFluid() == Fluids.WATER);
				world.setBlockState(blockPos, blockState2);
			}

		}
	}

	public static boolean canGrowIn(BlockState state) {
		return state.isAir() || state.isOf(Blocks.WATER) && state.getFluidState().getLevel() == 8;
	}
}
