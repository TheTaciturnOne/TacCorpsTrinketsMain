package com.thetaciturnone.taccorpstrinkets.utils;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;

public class HammerSlamParticle extends SpriteBillboardParticle {
	private final SpriteProvider sprites;
	private final float alphaDecrease;
	private float prevAlpha;
	public HammerSlamParticle(ClientWorld clientWorld, double d, double e, double f, double g, SpriteProvider spriteProvider) {
		super(clientWorld, d, e, f);
		this.maxAge = 25;
		this.scale = 2.8F;
		this.alpha = 1;
		this.alphaDecrease = 1F / Math.max(this.maxAge, 1.0F);
		this.sprites = spriteProvider;
		this.setSpriteForAge(spriteProvider);
	}
	@Override
	public void tick() {
		super.tick();
		this.prevAlpha = alpha;
		if(this.alpha > 0.0F){
			this.setSpriteForAge(sprites);
			this.alpha = Math.max(this.alpha - alphaDecrease, 0.0F);
		}
	}

	@Override
	public ParticleTextureSheet getType() {
		return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
	}

	public float getSize(float tickDelta) {
		return this.scale * MathHelper.clamp(((float)this.age + tickDelta) / (float)this.maxAge * 0.5F, 0.0F, 1.0F);
	}

	@Environment(EnvType.CLIENT)
	public static class Factory implements ParticleFactory<DefaultParticleType> {
		private final SpriteProvider spriteProvider;

		public Factory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
			return new HammerSlamParticle(clientWorld, d, e, f, g, this.spriteProvider);
		}
	}
}
