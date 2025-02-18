package com.thetaciturnone.taccorpstrinkets.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

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
		boolean isInWater = entityIn.isSubmergedInWater();
		float rotSpeed = isInWater ? 10.0F : 135.0F;
		BakedModel ibakedmodel = this.itemRenderer.getModel(itemStack, entityIn.getWorld(), null, 1);
		matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(entityIn.getYaw() - 270.0f));
		matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(MathHelper.lerp(tickDelta, entityIn.prevPitch, entityIn.getPitch()) + rotSpeed * age));
		matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90.0f));
		itemRenderer.renderItem(itemStack, ModelTransformationMode.THIRD_PERSON_RIGHT_HAND, false, matrixStack, bufferIn, packedLightIn, OverlayTexture.DEFAULT_UV, ibakedmodel);
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
