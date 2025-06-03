package com.thetaciturnone.taccorpstrinkets.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.thetaciturnone.taccorpstrinkets.TacCorpsTrinkets;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(KeyBinding.class)
public abstract class KeyBindingMixin {

	@Shadow
	@Final
	private String category;

	@Shadow
	@Final
	public static String GAMEPLAY_CATEGORY;

	@Shadow
	@Final
	public static String MOVEMENT_CATEGORY;

	@ModifyReturnValue(method = "wasPressed", at = @At("RETURN"))
	private boolean tacCorp$stunningKeybindWas(boolean original) {
		if (MinecraftClient.getInstance().getCameraEntity() instanceof PlayerEntity playerEntity) {
			if (playerEntity.hasStatusEffect(TacCorpsTrinkets.STUNNED) && !playerEntity.isCreative() && !playerEntity.isSpectator() && !playerEntity.isUsingItem()) {
				if (this.category.equals(GAMEPLAY_CATEGORY) || this.category.equals(MOVEMENT_CATEGORY)) {
					return false;
				}
			}
		}
		return original;
	}

	@ModifyReturnValue(method = "isPressed", at = @At("RETURN"))
	private boolean tacCorp$stunningKeybindIs(boolean original) {
		if (MinecraftClient.getInstance().getCameraEntity() instanceof PlayerEntity playerEntity) {
			if (playerEntity.hasStatusEffect(TacCorpsTrinkets.STUNNED) && !playerEntity.isCreative() && !playerEntity.isSpectator() && !playerEntity.isUsingItem()) {
				if (this.category.equals(GAMEPLAY_CATEGORY) || this.category.equals(MOVEMENT_CATEGORY)) {
					return false;
				}
			}
		}
		return original;
	}


}
