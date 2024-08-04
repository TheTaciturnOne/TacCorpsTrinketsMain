package com.thetaciturnone.taccorpstrinkets.entity;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.thetaciturnone.taccorpstrinkets.entity.ThrownHammerEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;
import org.joml.Quaternionf;

import static java.lang.Math.PI;

@Environment(EnvType.CLIENT)
public class ThrownHammerEntityRenderer extends EntityRenderer<ThrownHammerEntity> {
	public final ItemRenderer itemRenderer;

	public ThrownHammerEntityRenderer(EntityRendererFactory.Context context) {
		super(context);
		this.itemRenderer = context.getItemRenderer();
		this.shadowRadius = 0.5F;
		this.shadowOpacity = 0.5F;
	}

	public void render(ThrownHammerEntity entityIn, float entityYaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider bufferIn, int packedLightIn) {
		matrixStack.push();
		ItemStack itemStack = entityIn.getItem();
		float age = entityIn.getAgeException();
		BakedModel ibakedmodel = this.itemRenderer.getHeldItemModel(itemStack, entityIn.world, null, 1);
		matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(MathHelper.lerp(tickDelta, entityIn.prevYaw, entityIn.getYaw()) - 270.0f));
		matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.lerp(tickDelta, entityIn.prevPitch, entityIn.getPitch()) + 135.0f * age));
		matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90.0f));
		itemRenderer.renderItem(itemStack, ModelTransformation.Mode.FIRST_PERSON_RIGHT_HAND, false, matrixStack, bufferIn, packedLightIn, OverlayTexture.DEFAULT_UV, ibakedmodel);
		matrixStack.scale(1, 1, 1);
		matrixStack.translate(0.1f, -0.2f, 0.0f);


		matrixStack.pop();

		super.render(entityIn, entityYaw, tickDelta, matrixStack, bufferIn, packedLightIn);
	}

	@Override
	public Identifier getTexture(ThrownHammerEntity entity) {
		return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
	}
}
