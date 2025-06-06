package com.thetaciturnone.taccorpstrinkets.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.thetaciturnone.taccorpstrinkets.TacCorpsTrinkets;
import com.thetaciturnone.taccorpstrinkets.registries.TacBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.NoteBlock;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(NoteBlock.class)
public class NoteBlockMixin {

	@WrapOperation(method = "onSyncedBlockEvent", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/enums/NoteBlockInstrument;getSound()Lnet/minecraft/registry/entry/RegistryEntry;"))
	private RegistryEntry<SoundEvent> tacCorp$tacMeowNoteBlock(NoteBlockInstrument instrument, Operation<RegistryEntry<SoundEvent>> original, BlockState state, World world, BlockPos pos, int type, int data) {
		if (world.getBlockState(pos.down()).isOf(TacBlocks.TAC_PLUSHIE)) {
			return Registries.SOUND_EVENT.getEntry(TacCorpsTrinkets.TAC_BOOPED_SOUND_EVENT);
		}
		return original.call(instrument);
	}


}
