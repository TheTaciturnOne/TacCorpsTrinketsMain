package com.thetaciturnone.taccorpstrinkets.blocks;

import com.thetaciturnone.taccorpstrinkets.registries.TacItems;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class QuartzClusterBlock extends QuartzCrystalBlock implements Waterloggable {
	public static final BooleanProperty WATERLOGGED;
	public static final DirectionProperty FACING;
	protected final VoxelShape northShape;
	protected final VoxelShape southShape;
	protected final VoxelShape eastShape;
	protected final VoxelShape westShape;
	protected final VoxelShape upShape;
	protected final VoxelShape downShape;

	public QuartzClusterBlock(int height, int xzOffset, AbstractBlock.Settings settings) {
		super(settings);
		this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false).with(FACING, Direction.UP));
		this.upShape = Block.createCuboidShape(xzOffset, 0.0, xzOffset, 16 - xzOffset, height, 16 - xzOffset);
		this.downShape = Block.createCuboidShape(xzOffset, 16 - height, xzOffset, 16 - xzOffset, 16.0, 16 - xzOffset);
		this.northShape = Block.createCuboidShape(xzOffset, xzOffset, 16 - height, 16 - xzOffset, 16 - xzOffset, 16.0);
		this.southShape = Block.createCuboidShape(xzOffset, xzOffset, 0.0, 16 - xzOffset, 16 - xzOffset, height);
		this.eastShape = Block.createCuboidShape(0.0, xzOffset, xzOffset, height, 16 - xzOffset, 16 - xzOffset);
		this.westShape = Block.createCuboidShape(16 - height, xzOffset, xzOffset, 16.0, 16 - xzOffset, 16 - xzOffset);
	}

	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		Direction direction = state.get(FACING);
		return switch (direction) {
			case NORTH -> this.northShape;
			case SOUTH -> this.southShape;
			case EAST -> this.eastShape;
			case WEST -> this.westShape;
			case DOWN -> this.downShape;
			case UP -> this.upShape;
		};
	}

	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		Direction direction = state.get(FACING);
		BlockPos blockPos = pos.offset(direction.getOpposite());
		return world.getBlockState(blockPos).isSideSolidFullSquare(world, blockPos, direction);
	}

	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (state.get(WATERLOGGED)) {
			world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		}

		return direction == state.get(FACING).getOpposite() && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

	@Nullable
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		WorldAccess worldAccess = ctx.getWorld();
		BlockPos blockPos = ctx.getBlockPos();
		return this.getDefaultState().with(WATERLOGGED, worldAccess.getFluidState(blockPos).getFluid() == Fluids.WATER).with(FACING, ctx.getSide());
	}

	public BlockState rotate(BlockState state, BlockRotation rotation) {
		return state.with(FACING, rotation.rotate(state.get(FACING)));
	}

	public BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.rotate(mirror.getRotation(state.get(FACING)));
	}

	public FluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
	}

	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED, FACING);
	}

	@Override
	protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (stack.getItem().equals(TacItems.NETHERITE_SLAG)){
			if (!player.getAbilities().creativeMode) {
				stack.decrement(1);
			}
			world.breakBlock(pos, false);
			dropStack(world, pos, new ItemStack(TacItems.QUARTZITE));
			if (player instanceof ServerPlayerEntity serverPlayerEntity) {
				serverPlayerEntity.incrementStat(Stats.CRAFTED.getOrCreateStat(TacItems.QUARTZITE));
			}
			return ItemActionResult.SUCCESS;
		} else
			return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
	}

	static {
		WATERLOGGED = Properties.WATERLOGGED;
		FACING = Properties.FACING;
	}
}
