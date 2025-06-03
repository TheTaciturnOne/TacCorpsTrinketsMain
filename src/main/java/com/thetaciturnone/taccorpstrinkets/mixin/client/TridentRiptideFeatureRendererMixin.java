package com.thetaciturnone.taccorpstrinkets.mixin.client;

import com.thetaciturnone.taccorpstrinkets.registries.TacItems;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.TridentRiptideFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static com.thetaciturnone.taccorpstrinkets.TacCorpsTrinketsClient.HAMMER_BOOST_TEXTURE;


@Mixin(TridentRiptideFeatureRenderer.class)
public abstract class TridentRiptideFeatureRendererMixin {
    @ModifyVariable(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V", at = @At("STORE"))
    private VertexConsumer tacCorp$flingingHammerRiptideRenderer(VertexConsumer orig, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, LivingEntity livingEntity) {
        if (livingEntity instanceof PlayerEntity player && player.isUsingRiptide() && (player.getMainHandStack().isOf(TacItems.QUARTZITE_HAMMER) || player.getMainHandStack().isOf(TacItems.SHATTERED_QUARTZITE_HAMMER)
		|| player.getOffHandStack().isOf(TacItems.QUARTZITE_HAMMER) || player.getOffHandStack().isOf(TacItems.SHATTERED_QUARTZITE_HAMMER)) && (player.getItemCooldownManager().isCoolingDown(TacItems.QUARTZITE_HAMMER) || player.getItemCooldownManager().isCoolingDown(TacItems.SHATTERED_QUARTZITE_HAMMER))) {
            return vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutoutNoCull(HAMMER_BOOST_TEXTURE));
        }
        return orig;
    }
}
