package com.thetaciturnone.taccorpstrinkets.client.renderer;

import dev.emi.trinkets.api.client.TrinketRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.Unit;
import net.minecraft.util.profiler.Profiler;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class SilentMaskItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer, IdentifiableResourceReloadListener{
	private final Identifier rendererId;
	private final Identifier itemId;
	private ItemRenderer itemRenderer;
	private BakedModel inventoryModel;
	private BakedModel wornModel;

	public SilentMaskItemRenderer(Identifier itemId) {
		this.rendererId = new Identifier(itemId.getNamespace(), itemId.getPath() + "_renderer");
		this.itemId = itemId;
	}

	public Identifier getFabricId() {
		return this.rendererId;
	}

	public CompletableFuture<Void> reload(Synchronizer synchronizer, ResourceManager manager, Profiler prepareProfiler, Profiler applyProfiler, Executor prepareExecutor, Executor applyExecutor) {
		return synchronizer.whenPrepared(Unit.INSTANCE).thenRunAsync(() -> {
			applyProfiler.startTick();
			applyProfiler.push("listener");
			MinecraftClient client = MinecraftClient.getInstance();
			this.itemRenderer = client.getItemRenderer();
			this.inventoryModel = client.getBakedModelManager().getModel(new ModelIdentifier(this.itemId, "inventory"));
			this.wornModel = client.getBakedModelManager().getModel(new ModelIdentifier(this.itemId + "_worn", "inventory"));
			applyProfiler.pop();
			applyProfiler.endTick();
		}, applyExecutor);
	}

	public void render(ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		matrices.pop();
		matrices.push();
		if (mode == ModelTransformation.Mode.GUI || mode == ModelTransformation.Mode.GROUND || mode == ModelTransformation.Mode.FIXED
			|| mode == ModelTransformation.Mode.FIRST_PERSON_RIGHT_HAND || mode == ModelTransformation.Mode.FIRST_PERSON_LEFT_HAND
			|| mode == ModelTransformation.Mode.THIRD_PERSON_RIGHT_HAND || mode == ModelTransformation.Mode.THIRD_PERSON_LEFT_HAND ) {
			this.itemRenderer.renderItem(stack, mode, false, matrices, vertexConsumers, light, overlay, this.inventoryModel);
		}  else {
			boolean leftHanded;
			switch (mode) {
				case FIRST_PERSON_LEFT_HAND, THIRD_PERSON_LEFT_HAND -> leftHanded = true;
				default -> leftHanded = false;
			}
			this.itemRenderer.renderItem(stack, mode, leftHanded, matrices, vertexConsumers, light, overlay, this.wornModel);
		}
	}
}
