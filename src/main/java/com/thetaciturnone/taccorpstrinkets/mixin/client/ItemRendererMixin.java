package com.thetaciturnone.taccorpstrinkets.mixin.client;

import com.thetaciturnone.taccorpstrinkets.TacCorpsTrinkets;
import com.thetaciturnone.taccorpstrinkets.item.QuartziteHammerItem;
import com.thetaciturnone.taccorpstrinkets.item.BaseHammerItem;
import com.thetaciturnone.taccorpstrinkets.item.SilentMaskItem;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

	@Shadow
	public abstract ItemModels getModels();

	@ModifyVariable(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At(value = "HEAD"), argsOnly = true)
	public BakedModel tacCorp$largeHammerModel(BakedModel value, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		if (stack.getItem() instanceof QuartziteHammerItem) {
			boolean handheld = (renderMode != ModelTransformationMode.GUI && renderMode != ModelTransformationMode.GROUND);
			if (QuartziteHammerItem.getVariant(stack) != 0 || handheld) { // trying to parse in the default gui hammer model breaks it, so now it skips that one
				return this.getModels().getModelManager().getModel(QuartziteHammerItem.getHammerModelIdentifier(stack, handheld));
			}
		}
		else if (stack.getItem() instanceof BaseHammerItem && renderMode != ModelTransformationMode.GUI && renderMode != ModelTransformationMode.GROUND) {
			return this.getModels().getModelManager().getModel(TacCorpsTrinkets.id("item/shattered_quartzite_hammer_handheld"));
		}
		else if (stack.getItem() instanceof SilentMaskItem && renderMode == ModelTransformationMode.HEAD) {
			return this.getModels().getModelManager().getModel(TacCorpsTrinkets.id("item/mask_of_silence_worn"));
		}
		return value;
	}
}
