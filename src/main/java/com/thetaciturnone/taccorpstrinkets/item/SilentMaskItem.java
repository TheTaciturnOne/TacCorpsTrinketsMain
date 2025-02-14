package com.thetaciturnone.taccorpstrinkets.item;

import com.thetaciturnone.taccorpstrinkets.client.renderer.InventoryGooberRenderer;
import com.thetaciturnone.taccorpstrinkets.client.renderer.SilentMaskItemRenderer;
import com.thetaciturnone.taccorpstrinkets.registries.TacItems;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketItem;
import dev.emi.trinkets.api.TrinketsApi;
import dev.emi.trinkets.api.client.TrinketRenderer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ResourceType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import xyz.amymialee.mialeemisc.client.InventoryItemRenderer;

import java.util.List;
import java.util.Optional;

public class SilentMaskItem extends TrinketItem {
	public SilentMaskItem(Settings settings) {
		super(settings);
	}

	public static boolean isWearingMask(LivingEntity livingEntity) {
		return getWornMask(livingEntity) != ItemStack.EMPTY;
	}

	public static ItemStack getWornMask(LivingEntity livingEntity) {
		Optional<TrinketComponent> component = TrinketsApi.getTrinketComponent(livingEntity);
		if (component.isPresent()) {
			for (Pair<SlotReference, ItemStack> pair : component.get().getAllEquipped()) {
				if (pair.getRight().getItem() instanceof SilentMaskItem) {
					return pair.getRight();
				}
			}
		}
		return ItemStack.EMPTY;
	}

	public static int getOffset(ItemStack stack) {
		if (stack.getNbt() == null) {
			return 0;
		}
		return stack.getNbt().getInt("offset");
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
		tooltip.add(Text.literal("The face of the company!").formatted(Formatting.DARK_AQUA));
		super.appendTooltip(stack, world, tooltip, context);
	}
}
