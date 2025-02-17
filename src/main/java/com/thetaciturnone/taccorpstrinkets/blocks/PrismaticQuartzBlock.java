package com.thetaciturnone.taccorpstrinkets.blocks;

import com.thetaciturnone.taccorpstrinkets.TacCorpsTrinkets;
import com.thetaciturnone.taccorpstrinkets.blocks.entities.PrismaticQuartzBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class PrismaticQuartzBlock extends BlockWithEntity {
	public static final BooleanProperty LIT;

	public PrismaticQuartzBlock(AbstractBlock.Settings settings) {
		super(settings);
		this.setDefaultState(this.getDefaultState().with(LIT, false));
	}

	public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
		if (!world.isClient) {
			BlockPos lightPos1 = pos.down(1);
			BlockState lightState1 = world.getBlockState(lightPos1);
			boolean bl = state.get(LIT);
			if (bl != getLitState((WorldAccess) lightState1, pos)) {
				if (bl) {
					world.scheduleBlockTick(pos, this, 2);
				} else {
					world.setBlockState(pos, state.cycle(LIT), 2);
				}
			}
		}
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new PrismaticQuartzBlockEntity(pos, state);
	}

	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return checkType(type, TacCorpsTrinkets.PRISMATIC_ENTITY, PrismaticQuartzBlockEntity::tick);
	}

	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext context) {
		return getDefaultState().with(LIT, getLitState(context.getWorld(), context.getBlockPos()));
	}

	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.MODEL;
	}

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		BlockPos lightPos1 = pos.down(1);
		BlockState lightState1 = world.getBlockState(lightPos1);
		if (state.get(LIT) && getLitState((WorldAccess) lightState1, pos)) {
			world.setBlockState(pos, state.cycle(LIT), 2);
		}
	}

	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(LIT);
	}

	private boolean getLitState(WorldAccess worldAccess, BlockPos pos) {
		return worldAccess.getBlockState(pos.down()).isIn(TacCorpsTrinkets.LIGHT_SOURCE_BLOCK_TAG);
	}
	static {
		LIT = RedstoneTorchBlock.LIT;
	}
}
