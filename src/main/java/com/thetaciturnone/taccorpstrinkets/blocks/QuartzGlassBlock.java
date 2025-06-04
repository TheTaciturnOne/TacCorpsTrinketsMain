package com.thetaciturnone.taccorpstrinkets.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.TransparentBlock;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class QuartzGlassBlock extends TransparentBlock {
    public QuartzGlassBlock(Settings settings) {
        super(settings);
    }

	protected static final VoxelShape COLLISION_SHAPE = Block.createCuboidShape(2.0, 2.0, 2.0, 14.0, 14.0, 14.0);
	public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return COLLISION_SHAPE;
	}

	public VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
		return VoxelShapes.fullCube();
	}

	public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return VoxelShapes.fullCube();
	}

	@Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
		super.onEntityCollision(state, world, pos, entity);
		if (world instanceof ServerWorld && entity instanceof Entity) {
			world.breakBlock(new BlockPos(pos), false, entity);
		}
	}

	@Override
	public void onEntityLand(BlockView world, Entity entity) {

	}

	@Override
	public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {

	}

}
