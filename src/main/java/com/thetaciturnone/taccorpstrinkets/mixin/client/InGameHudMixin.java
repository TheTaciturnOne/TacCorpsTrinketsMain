package com.thetaciturnone.taccorpstrinkets.mixin.client;

import com.thetaciturnone.taccorpstrinkets.TacCorpsTrinkets;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

	@Unique
	private static final Identifier FRAGILITY_OVERLAY = TacCorpsTrinkets.id("textures/misc/stunned_overlay.png");

	@Shadow
	protected abstract void renderOverlay(DrawContext context, Identifier texture, float opacity);

	@Inject(method = "renderMiscOverlays", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getFrozenTicks()I"))
	private void winWeapons$renderWinningHandOverlays(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
		if (MinecraftClient.getInstance().getCameraEntity() instanceof LivingEntity livingEntity) {
			if (livingEntity.hasStatusEffect(TacCorpsTrinkets.STUNNED)) {
				int stunnedTicks = livingEntity.getStatusEffect(TacCorpsTrinkets.STUNNED).getDuration();
				if (stunnedTicks > 0) {
					float opacity = stunnedTicks > 50 ? 1f : stunnedTicks / 50f;
					this.renderOverlay(context, FRAGILITY_OVERLAY, opacity);
				}
			}
		}
	}


}
