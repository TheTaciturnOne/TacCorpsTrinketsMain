package com.thetaciturnone.taccorpstrinkets.mixin;

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
    @ModifyVariable(method = "render", at = @At("STORE"))
    private VertexConsumer swapHotRiptide(VertexConsumer orig, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, LivingEntity livingEntity) {
        if (livingEntity instanceof PlayerEntity && livingEntity.isUsingRiptide() && (livingEntity.getMainHandStack().getItem() == TacItems.QUARTZITE_HAMMER || (livingEntity.getOffHandStack().getItem() == TacItems.QUARTZITE_HAMMER) || (livingEntity.getMainHandStack().getItem() == TacItems.SHATTERED_QUARTZITE_HAMMER || (livingEntity.getOffHandStack().getItem() == TacItems.SHATTERED_QUARTZITE_HAMMER)))) {
            return vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutoutNoCull(HAMMER_BOOST_TEXTURE));
        }
        return orig;
    }
}
