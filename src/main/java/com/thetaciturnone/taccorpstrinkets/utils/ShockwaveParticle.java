package com.thetaciturnone.taccorpstrinkets.utils;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Quaternionf;
import org.joml.Vector3f;


@Environment(EnvType.CLIENT)
public class ShockwaveParticle extends SpriteBillboardParticle {
    private final SpriteProvider sprites;
	private float prevAlpha;
	private final float alphaDecrease;
    private static final Quaternionf QUATERNION = new Quaternionf(0F, -0.7F, 0.7F, 0F);

    ShockwaveParticle(ClientWorld world, double x, double y, double z, double velX, double velY, double velZ, SpriteProvider sprites) {
		super(world, x, y + 0.5, z, 0.0, 0.0, 0.0);
		this.scale = 8f;
		this.alpha = 1;
		this.setVelocity(0D, 0D, 0D);
		this.maxAge = 35;
		this.alphaDecrease = 1F / Math.max(this.maxAge, 1.0F);
		this.sprites = sprites;
		this.setSpriteForAge(sprites);
    }


    @Override
    public void buildGeometry(VertexConsumer buffer, Camera camera, float ticks) {
        Vec3d vec3 = camera.getPos();
        float x = (float) (MathHelper.lerp(ticks, this.prevPosX, this.x) - vec3.getX());
        float y = (float) (MathHelper.lerp(ticks, this.prevPosY, this.y) - vec3.getY());
        float z = (float) (MathHelper.lerp(ticks, this.prevPosZ, this.z) - vec3.getZ());

		Vector3f[] alphas = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
        // Additional vertices for underside faces
		Vector3f[] alphasBottom = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, -1.0F, 0.0F)};

        //float f4 = this.getSize(ticks);

        for (int i = 0; i < 4; ++i) {
			Vector3f alpha = alphas[i];
            alpha.rotate(QUATERNION);
			//alpha.scale(f4);
			alpha.mul(this.getSize(ticks));
            alpha.add(x, y, z);

            // Create additional vertices for underside faces
			Vector3f alphaBottom = alphasBottom[i];
            alphaBottom.rotate(QUATERNION);
            //alphaBottom.scale(f4);
            alphaBottom.add(x, y - 0.1F, z); // Slightly lower to avoid z-fighting
        }

        float f7 = this.getMinU();
        float f8 = this.getMaxU();
        float f5 = this.getMinV();
        float f6 = this.getMaxV();
        int light = this.getBrightness(ticks);

        // Render the top faces
        buffer.vertex(alphas[0].x(), alphas[0].y(), alphas[0].z()).texture(f8, f6).color(this.red, this.green, this.blue, this.alpha).light(light).next();
        buffer.vertex(alphas[1].x(), alphas[1].y(), alphas[1].z()).texture(f8, f5).color(this.red, this.green, this.blue, this.alpha).light(light).next();
        buffer.vertex(alphas[2].x(), alphas[2].y(), alphas[2].z()).texture(f7, f5).color(this.red, this.green, this.blue, this.alpha).light(light).next();
        buffer.vertex(alphas[3].x(), alphas[3].y(), alphas[3].z()).texture(f7, f6).color(this.red, this.green, this.blue, this.alpha).light(light).next();

        // Render the underside faces
        buffer.vertex(alphas[3].x(), alphas[3].y(), alphas[3].z()).texture(f7, f6).color(this.red, this.green, this.blue, this.alpha).light(light).next();
        buffer.vertex(alphas[2].x(), alphas[2].y(), alphas[2].z()).texture(f7, f5).color(this.red, this.green, this.blue, this.alpha).light(light).next();
        buffer.vertex(alphas[1].x(), alphas[1].y(), alphas[1].z()).texture(f8, f5).color(this.red, this.green, this.blue, this.alpha).light(light).next();
        buffer.vertex(alphas[0].x(), alphas[0].y(), alphas[0].z()).texture(f8, f6).color(this.red, this.green, this.blue, this.alpha).light(light).next();
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

	public float getSize(float tickDelta) {
		return this.scale * MathHelper.clamp(((float)this.age + tickDelta) / (float)this.maxAge * 0.75F, 0.0F, 1.0F);
	}

    @Override
    public void tick() {
        super.tick();
		this.prevAlpha = alpha;
		this.setSpriteForAge(sprites);
		if(this.alpha > 0.0F){
			this.alpha = Math.max(this.alpha - alphaDecrease, 0.0F);
		}
    }

    @Environment(EnvType.CLIENT)
    public record Factory(SpriteProvider sprites) implements ParticleFactory<DefaultParticleType> {
        public Particle createParticle(DefaultParticleType type, ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new ShockwaveParticle(world, x, y, z, xSpeed, ySpeed, zSpeed, sprites);
        }
    }
}
