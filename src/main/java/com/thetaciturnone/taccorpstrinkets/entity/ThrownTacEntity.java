package com.thetaciturnone.taccorpstrinkets.entity;

import com.thetaciturnone.taccorpstrinkets.TacCorpsTrinkets;
import com.thetaciturnone.taccorpstrinkets.registries.TacBlocks;
import com.thetaciturnone.taccorpstrinkets.registries.TacEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class ThrownTacEntity extends ThrownItemEntity {

    public ThrownTacEntity(EntityType<ThrownTacEntity> shotBreadEntityEntityType, World world) {
        super(shotBreadEntityEntityType, world);
    }

    public ThrownTacEntity(World world, LivingEntity owner) {
        super(TacEntities.THROWN_TAC, owner, world);
    }

    public ThrownTacEntity(World world, double x, double y, double z) {
        super(TacEntities.THROWN_TAC, x, y, z, world);
    }

	protected Item getDefaultItem() {
        return TacBlocks.TAC_PLUSHIE.asItem();
    }

    public void handleStatus(byte status) {
        if (status == 3) {
            ParticleEffect particleEffect = this.getParticleParameters();

            for (int i = 0; i < 8; ++i) {
                this.getWorld().addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            }
        }

    }
    private ParticleEffect getParticleParameters() {
        return new ItemStackParticleEffect(ParticleTypes.ITEM, TacBlocks.TAC_PLUSHIE.asItem().getDefaultStack());
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.getWorld().isClient) {
            this.getWorld().sendEntityStatus(this, (byte)3);
			this.dropItem(TacBlocks.TAC_PLUSHIE.asItem());
			this.playSound(TacCorpsTrinkets.TAC_THROWHIT_SOUND_EVENT, 1.0f, 1.0f);
			this.discard();
        }
    }
}

