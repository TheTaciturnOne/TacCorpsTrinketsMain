package com.thetaciturnone.taccorpstrinkets.blocks;

import com.thetaciturnone.taccorpstrinkets.registries.TacBlocks;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.util.Random;

public class BuddingQuartzBlock extends QuartzCrystalBlock {
	public static final int GROW_CHANCE = 5;
	private static final Direction[] DIRECTIONS = Direction.values();

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, net.minecraft.util.math.random.Random random) {
		if (random.nextInt(5) == 0) {
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

	public PistonBehavior getPistonBehavior(BlockState state) {
		return PistonBehavior.DESTROY;
	}
	public BuddingQuartzBlock(Settings settings) {
		super(settings);
	}

	public static boolean canGrowIn(BlockState state) {
		return state.isAir() || state.isOf(Blocks.WATER) && state.getFluidState().getLevel() == 8;
	}
}
