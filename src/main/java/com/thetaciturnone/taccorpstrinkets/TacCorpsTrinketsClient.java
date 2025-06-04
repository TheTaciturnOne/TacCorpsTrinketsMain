package com.thetaciturnone.taccorpstrinkets;

import com.thetaciturnone.taccorpstrinkets.client.renderer.TacPlushieBlockEntityRenderer;
import com.thetaciturnone.taccorpstrinkets.entity.ThrownHammerEntityRenderer;
import com.thetaciturnone.taccorpstrinkets.entity.ThrownTacEntityRenderer;
import com.thetaciturnone.taccorpstrinkets.registries.TacBlocks;
import com.thetaciturnone.taccorpstrinkets.registries.TacEntities;
import com.thetaciturnone.taccorpstrinkets.supporter.SupporterUtils;
import com.thetaciturnone.taccorpstrinkets.utils.HammerSlamParticle;
import com.thetaciturnone.taccorpstrinkets.utils.ShockwaveParticle;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.impl.blockrenderlayer.BlockRenderLayerMapImpl;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.util.Identifier;

public class TacCorpsTrinketsClient implements ClientModInitializer {
	public static final Identifier HAMMER_BOOST_TEXTURE = TacCorpsTrinkets.id("textures/entity/hammer_boost.png");

	@Override
	public void onInitializeClient() {
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.player != null && SupporterUtils.CRASH_CONTROL && !SupporterUtils.isPlayerSupporter(client.player)){
				throw new RuntimeException("This is a Supporter only mod! Sorry!");
			}
		});

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

		BlockEntityRendererFactories.register(TacCorpsTrinkets.TAC_PLUSH_BLOCK_ENTITY, TacPlushieBlockEntityRenderer::new);

		ModelLoadingPlugin.register((context) -> {
			context.addModels(TacCorpsTrinkets.id("item/quartzite_hammer_handheld"));
			context.addModels(TacCorpsTrinkets.id("item/shattered_quartzite_hammer_handheld"));
			context.addModels(TacCorpsTrinkets.id("item/mask_of_silence_worn"));

			context.addModels(TacCorpsTrinkets.id("item/dedede_quartzite_hammer"));
			context.addModels(TacCorpsTrinkets.id("item/dedede_quartzite_hammer_handheld"));
			context.addModels(TacCorpsTrinkets.id("item/pico_pico_quartzite_hammer"));
			context.addModels(TacCorpsTrinkets.id("item/pico_pico_quartzite_hammer_handheld"));
			context.addModels(TacCorpsTrinkets.id("item/spamton_quartzite_hammer"));
			context.addModels(TacCorpsTrinkets.id("item/spamton_quartzite_hammer_handheld"));
			context.addModels(TacCorpsTrinkets.id("item/big_bell_quartzite_hammer"));
			context.addModels(TacCorpsTrinkets.id("item/big_bell_quartzite_hammer_handheld"));
		});
	}
}
