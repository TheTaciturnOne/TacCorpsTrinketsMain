package com.thetaciturnone.taccorpstrinkets;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.thetaciturnone.taccorpstrinkets.client.renderer.TacPlushieBlockEntityRenderer;
import com.thetaciturnone.taccorpstrinkets.entity.ThrownHammerEntityRenderer;
import com.thetaciturnone.taccorpstrinkets.entity.ThrownTacEntityRenderer;
import com.thetaciturnone.taccorpstrinkets.registries.TacBlocks;
import com.thetaciturnone.taccorpstrinkets.registries.TacEntities;
import com.thetaciturnone.taccorpstrinkets.utils.HammerSlamParticle;
import com.thetaciturnone.taccorpstrinkets.utils.ShockwaveParticle;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.impl.blockrenderlayer.BlockRenderLayerMapImpl;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class TacCorpsTrinketsClient implements ClientModInitializer {
	public static final Identifier HAMMER_BOOST_TEXTURE = TacCorpsTrinkets.id("textures/entity/hammer_boost.png");

	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(TacEntities.THROWN_HAMMER, ThrownHammerEntityRenderer::new);
		EntityRendererRegistry.register(TacEntities.THROWN_TAC, ThrownTacEntityRenderer::new);
		BlockRenderLayerMapImpl.INSTANCE.putBlock(TacBlocks.QUARTZ_GLASS_PANE, RenderLayer.getTranslucent());
		BlockRenderLayerMapImpl.INSTANCE.putBlock(TacBlocks.QUARTZ_GLASS, RenderLayer.getTranslucent());
		BlockRenderLayerMapImpl.INSTANCE.putBlock(TacBlocks.PRISMATIC_QUARTZ_CRYSTAL, RenderLayer.getTranslucent());
		BlockRenderLayerMapImpl.INSTANCE.putBlock(TacBlocks.NETHERITE_STRENGTHENED_QUARTZ_GLASS_PANE, RenderLayer.getTranslucent());
		BlockRenderLayerMapImpl.INSTANCE.putBlock(TacBlocks.NETHERITE_STRENGTHENED_QUARTZ_GLASS, RenderLayer.getTranslucent());
		BlockRenderLayerMapImpl.INSTANCE.putBlock(TacBlocks.QUARTZ_CRYSTAL_CLUSTER, RenderLayer.getCutout());
		BlockRenderLayerMapImpl.INSTANCE.putBlock(TacBlocks.LARGE_QUARTZ_CRYSTAL_CLUSTER, RenderLayer.getCutout());
		BlockRenderLayerMapImpl.INSTANCE.putBlock(TacBlocks.MEDIUM_QUARTZ_CRYSTAL_CLUSTER, RenderLayer.getCutout());
		BlockRenderLayerMapImpl.INSTANCE.putBlock(TacBlocks.SMALL_QUARTZ_CRYSTAL_CLUSTER, RenderLayer.getCutout());

		ParticleFactoryRegistry.getInstance().register(TacCorpsTrinkets.HAMMER_SLAM, HammerSlamParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(TacCorpsTrinkets.HAMMER_WAVE, ShockwaveParticle.Factory::new);

		BlockEntityRendererRegistry.register(TacCorpsTrinkets.TAC_PLUSH_BLOCK_ENTITY, TacPlushieBlockEntityRenderer::new);

		ModelLoadingPlugin.register((context) -> {
			context.addModels(new ModelIdentifier(TacCorpsTrinkets.MOD_ID, "quartzite_hammer_handheld", "inventory"));
			context.addModels(new ModelIdentifier(TacCorpsTrinkets.MOD_ID, "shattered_quartzite_hammer_handheld", "inventory"));
			context.addModels(new ModelIdentifier(TacCorpsTrinkets.MOD_ID, "mask_of_silence_worn", "inventory"));

			context.addModels(new ModelIdentifier(TacCorpsTrinkets.MOD_ID, "dedede_quartzite_hammer", "inventory"));
			context.addModels(new ModelIdentifier(TacCorpsTrinkets.MOD_ID, "dedede_quartzite_hammer_handheld", "inventory"));
			context.addModels(new ModelIdentifier(TacCorpsTrinkets.MOD_ID, "pico_pico_quartzite_hammer", "inventory"));
			context.addModels(new ModelIdentifier(TacCorpsTrinkets.MOD_ID, "pico_pico_quartzite_hammer_handheld", "inventory"));
			context.addModels(new ModelIdentifier(TacCorpsTrinkets.MOD_ID, "hextech_quartzite_hammer", "inventory"));
			context.addModels(new ModelIdentifier(TacCorpsTrinkets.MOD_ID, "hextech_quartzite_hammer_handheld", "inventory"));
			context.addModels(new ModelIdentifier(TacCorpsTrinkets.MOD_ID, "spamton_quartzite_hammer", "inventory"));
			context.addModels(new ModelIdentifier(TacCorpsTrinkets.MOD_ID, "spamton_quartzite_hammer_handheld", "inventory"));
		});
	}

	public static void registerBlockingModelPredicateProviders(Item item) {
		ModelPredicateProviderRegistry.register(item, new Identifier("blocking"), (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0f : 0.0f);
	}
}
