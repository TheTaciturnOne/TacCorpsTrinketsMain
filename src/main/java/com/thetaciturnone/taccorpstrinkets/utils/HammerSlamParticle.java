package com.thetaciturnone.taccorpstrinkets.utils;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

public class HammerSlamParticle extends ExplosionLargeParticle {
	public HammerSlamParticle(ClientWorld clientWorld, double d, double e, double f, double g, SpriteProvider spriteProvider) {
		super(clientWorld, d, e, f, g, spriteProvider);
		this.maxAge = 8;
		this.scale = 0.8F;
		this.setSpriteForAge(spriteProvider);
	}

	public int getBrightness(float tint) {
		return 15728880;
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
