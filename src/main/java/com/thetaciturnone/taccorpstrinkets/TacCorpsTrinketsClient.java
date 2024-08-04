package com.thetaciturnone.taccorpstrinkets;

import com.thetaciturnone.taccorpstrinkets.registries.TacBlocks;
import com.thetaciturnone.taccorpstrinkets.client.render.item.BigWeaponRenderer;
import com.thetaciturnone.taccorpstrinkets.client.render.item.SilentMaskItemRenderer;
import com.thetaciturnone.taccorpstrinkets.registries.TacEntities;
import com.thetaciturnone.taccorpstrinkets.registries.TacItems;
import com.thetaciturnone.taccorpstrinkets.utils.HammerSlamParticle;
import com.thetaciturnone.taccorpstrinkets.entity.ThrownHammerEntityRenderer;
import com.thetaciturnone.taccorpstrinkets.utils.ShockwaveParticle;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.impl.blockrenderlayer.BlockRenderLayerMapImpl;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.resource.ResourceType;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import xyz.amymialee.mialeemisc.MialeeMiscClient;

import java.util.Set;

public class TacCorpsTrinketsClient implements ClientModInitializer {
	public static final Set<Item> MASK = new ReferenceOpenHashSet();
	@Override
	public void onInitializeClient(ModContainer mod) {
		EntityRendererRegistry.register(TacEntities.THROWN_HAMMER, ThrownHammerEntityRenderer::new);
		BlockRenderLayerMapImpl.INSTANCE.putBlock(TacBlocks.QUARTZ_GLASS_PANE, RenderLayer.getTranslucent());
		BlockRenderLayerMapImpl.INSTANCE.putBlock(TacBlocks.QUARTZ_GLASS, RenderLayer.getTranslucent());
		BlockRenderLayerMapImpl.INSTANCE.putBlock(TacBlocks.NETHERITE_STRENGTHENED_QUARTZ_GLASS_PANE, RenderLayer.getTranslucent());
		BlockRenderLayerMapImpl.INSTANCE.putBlock(TacBlocks.NETHERITE_STRENGTHENED_QUARTZ_GLASS, RenderLayer.getTranslucent());
		BlockRenderLayerMapImpl.INSTANCE.putBlock(TacBlocks.QUARTZ_CRYSTAL_CLUSTER, RenderLayer.getCutout());
		BlockRenderLayerMapImpl.INSTANCE.putBlock(TacBlocks.LARGE_QUARTZ_CRYSTAL_CLUSTER, RenderLayer.getCutout());
		BlockRenderLayerMapImpl.INSTANCE.putBlock(TacBlocks.MEDIUM_QUARTZ_CRYSTAL_CLUSTER, RenderLayer.getCutout());
		BlockRenderLayerMapImpl.INSTANCE.putBlock(TacBlocks.SMALL_QUARTZ_CRYSTAL_CLUSTER, RenderLayer.getCutout());
		ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> {
			registry.register(new Identifier("taccorpstrinkets", "particle/hammer_slam"));
		}));

		ParticleFactoryRegistry.getInstance().register(TacCorpsTrinkets.HAMMER_SLAM, HammerSlamParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(TacCorpsTrinkets.HAMMER_WAVE, ShockwaveParticle.Factory::new);

		registerHammerRenderer(TacItems.QUARTZITE_HAMMER);
		registerHammerRenderer(TacItems.SHATTERED_QUARTZITE_HAMMER);

		Identifier maskId = Registry.ITEM.getId(TacItems.MASK_OF_SILENCE);
		SilentMaskItemRenderer maskItemRenderer = new SilentMaskItemRenderer(maskId);
		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(maskItemRenderer);
		BuiltinItemRendererRegistry.INSTANCE.register(TacItems.MASK_OF_SILENCE, maskItemRenderer);
		ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> {
			out.accept(new ModelIdentifier(maskId, "inventory"));
			out.accept(new ModelIdentifier(maskId + "_worn", "inventory"));
		});
		MialeeMiscClient.INVENTORY_ITEMS.add(TacItems.MASK_OF_SILENCE);
	}

	private void registerHammerRenderer(ItemConvertible item) {
		Identifier bigId = Registry.ITEM.getId(item.asItem());
		BigWeaponRenderer bigItemRenderer = new BigWeaponRenderer(bigId);
		ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(bigItemRenderer);
		BuiltinItemRendererRegistry.INSTANCE.register(item, bigItemRenderer);
		ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> {
			out.accept(new ModelIdentifier(bigId + "_gui", "inventory"));
			out.accept(new ModelIdentifier(bigId + "_handheld", "inventory"));
		});
	}
}
