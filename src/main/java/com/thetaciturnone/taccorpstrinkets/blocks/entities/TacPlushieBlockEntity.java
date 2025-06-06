package com.thetaciturnone.taccorpstrinkets.blocks.entities;

import com.thetaciturnone.taccorpstrinkets.TacCorpsTrinkets;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class TacPlushieBlockEntity extends BlockEntity {
	public double squash;

	public TacPlushieBlockEntity(BlockPos pos, BlockState state) {
		super(TacCorpsTrinkets.TAC_PLUSH_BLOCK_ENTITY, pos, state);
	}

	public static void tick(World world, BlockPos pos, BlockState state, TacPlushieBlockEntity spark) {
		if (spark.squash > 0.0) {
			spark.squash /= 3.0;
			if (spark.squash < 0.009999999776482582) {
				spark.squash = 0.0;
				if (world != null) {
					world.updateListeners(pos, state, state, 2);
				}
			}
		}

	}

	public void squish(int squash) {
		this.squash += squash;
		if (this.world != null) {
			this.world.updateListeners(this.pos, this.getCachedState(), this.getCachedState(), 2);
		}

		this.markDirty();
	}

	@Override
	protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		nbt.putDouble("squash", this.squash);
	}

	@Override
	public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		this.squash = nbt.getDouble("squash");
	}

	@Nullable
	@Override
	public BlockEntityUpdateS2CPacket toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.create(this);
	}

	@Override
	public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
		return this.createNbt(registryLookup);
	}
}
