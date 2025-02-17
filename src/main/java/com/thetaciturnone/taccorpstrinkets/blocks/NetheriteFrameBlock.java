package com.thetaciturnone.taccorpstrinkets.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.stream.Stream;

public class NetheriteFrameBlock extends Block implements Waterloggable {
    public static BooleanProperty WATERLOGGED;
    public NetheriteFrameBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(WATERLOGGED, false));
    }

    private static VoxelShape SHAPE =
            Stream.of(
                    Block.createCuboidShape(0, 0, 0, 16, 2, 2),
                    Block.createCuboidShape(0, 0, 14, 16, 2, 16),
                    Block.createCuboidShape(0, 0, 2, 2, 2, 14),
                    Block.createCuboidShape(14, 0, 2, 16, 2, 14),
                    Block.createCuboidShape(0, 2, 0, 2, 14, 2),
                    Block.createCuboidShape(0, 2, 14, 2, 14, 16),
                    Block.createCuboidShape(14, 2, 0, 16, 14, 2),
                    Block.createCuboidShape(14, 2, 14, 16, 14, 16),
                    Block.createCuboidShape(0, 14, 0, 16, 16, 2),
                    Block.createCuboidShape(0, 14, 14, 16, 16, 16),
                    Block.createCuboidShape(-3.1898214703913594e-33, 14, 14, 16, 16, 16),
                    Block.createCuboidShape(0, 14, 2, 2, 16, 14),
                    Block.createCuboidShape(14, 14, 2, 16, 16, 14)
            ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();


    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return state;
    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        World world = ctx.getWorld();
        return this.getDefaultState().with(WATERLOGGED, world.getFluidState(blockPos).getFluid() == Fluids.WATER);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    static {
        WATERLOGGED = Properties.WATERLOGGED;
    }
}
