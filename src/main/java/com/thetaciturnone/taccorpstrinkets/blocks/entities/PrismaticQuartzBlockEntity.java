package com.thetaciturnone.taccorpstrinkets.blocks.entities;

import com.thetaciturnone.taccorpstrinkets.TacCorpsTrinkets;
import com.thetaciturnone.taccorpstrinkets.blocks.PrismaticQuartzBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

public class PrismaticQuartzBlockEntity extends BlockEntity {
	public PrismaticQuartzBlockEntity(BlockPos pos, BlockState state) {
		super(TacCorpsTrinkets.PRISMATIC_ENTITY, pos, state);
	}

	public static void tick(World world, BlockPos pos, BlockState state, PrismaticQuartzBlockEntity blockEntity) {
		long l = world.getTime();
		BlockPos lightPos1 = pos.down(1);
		BlockState lightState1 = world.getBlockState(lightPos1);
		if (state.get(PrismaticQuartzBlock.LIT)) {
			if (l % 80 == 0) {
				if (!lightState1.isIn(BlockTags.WOOL)) {
					world.playSound(null, pos, TacCorpsTrinkets.PRISMATIC_HUM_EVENT, SoundCategory.BLOCKS, 1.0F, 1.0F);
				}
			}
			Box box = new Box(pos).expand(10);
			for (LivingEntity entity : world.getNonSpectatingEntities(LivingEntity.class, box)) {
				if (l % 80 == 0) {
					if (entity.getType().isIn(EntityTypeTags.UNDEAD)) {
						entity.setFireTicks(40);
					}
					if (entity.isInvisible() || entity.getType().isIn(EntityTypeTags.UNDEAD)) {
						entity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 100, 0, true, true));
						entity.getWorld().addParticle(ParticleTypes.END_ROD,
							entity.getParticleX(0.5),
							entity.getRandomBodyY() - 0.25,
							entity.getParticleZ(0.5),
							0,
							0,
							0);
					}
				}
			}
		}
	}
}
