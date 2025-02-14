package com.thetaciturnone.taccorpstrinkets.registries;

import com.thetaciturnone.taccorpstrinkets.TacCorpsTrinkets;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;

public class TacSoundGroups extends BlockSoundGroup {

	public static BlockSoundGroup QUARTZ_CRYSTAL;

	static {
		QUARTZ_CRYSTAL = new BlockSoundGroup(1.0F, 1.0F,
			TacCorpsTrinkets.QUARTZ_CRYSTAL_BREAK_EVENT,
			TacCorpsTrinkets.QUARTZ_CRYSTAL_STEP_EVENT,
			TacCorpsTrinkets.QUARTZ_CRYSTAL_PLACE_EVENT,
			TacCorpsTrinkets.QUARTZ_CRYSTAL_HIT_EVENT,
			TacCorpsTrinkets.QUARTZ_CRYSTAL_FALL_EVENT);
	}

	public TacSoundGroups(float volume, float pitch, SoundEvent breakSound, SoundEvent stepSound, SoundEvent placeSound, SoundEvent hitSound, SoundEvent fallSound) {
		super(volume, pitch, breakSound, stepSound, placeSound, hitSound, fallSound);
	}
}
