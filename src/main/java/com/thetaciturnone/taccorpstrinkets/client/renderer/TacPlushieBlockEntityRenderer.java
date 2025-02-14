package com.thetaciturnone.taccorpstrinkets.client.renderer;

import com.thetaciturnone.taccorpstrinkets.blocks.entities.TacPlushieBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

public class TacPlushieBlockEntityRenderer<T extends BlockEntity> implements BlockEntityRenderer<T> {
	private final BlockRenderManager renderManager;

	public TacPlushieBlockEntityRenderer(BlockEntityRendererFactory.@NotNull Context context) {
		this.renderManager = context.getRenderManager();
	}

	@Override
	public void render(T entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		matrices.push();
		var squish = entity instanceof TacPlushieBlockEntity plushie ? plushie.squash : 0;
		var lastSquish = squish * 3;
		var squash = (float) Math.pow(1 - 1f / (1f + MathHelper.lerp(tickDelta, lastSquish, squish)), 2);
		matrices.scale(1, 1 - squash, 1);
		matrices.translate(0.5, 0, 0.5);
		matrices.scale(1 + squash / 2, 1, 1 + squash / 2);
		matrices.translate(-0.5, 0, -0.5);
		var state = entity.getCachedState();
		var bakedModel = this.renderManager.getModel(state);
		this.renderManager.getModelRenderer().render(matrices.peek(), vertexConsumers.getBuffer(RenderLayers.getEntityBlockLayer(state, false)), state, bakedModel, 0xFF, 0xFF, 0xFF, light, overlay);;
		matrices.pop();
	}
}
