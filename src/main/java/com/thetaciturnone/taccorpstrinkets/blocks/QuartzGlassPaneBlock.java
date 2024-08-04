package com.thetaciturnone.taccorpstrinkets.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.PaneBlock;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class QuartzGlassPaneBlock extends PaneBlock {
    public QuartzGlassPaneBlock(Settings settings) {
        super(settings);
    }

    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);
        if (world instanceof ServerWorld && entity instanceof Entity) {
            world.breakBlock(new BlockPos(pos), false, entity);
        }
    }
}
