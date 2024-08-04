package com.thetaciturnone.taccorpstrinkets.mixin;

import com.thetaciturnone.taccorpstrinkets.TacCorpsTrinkets;
import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
	@Inject(method = "init", at = @At("TAIL"))
	public void exampleMod$onInit(CallbackInfo ci) {
		TacCorpsTrinkets.LOGGER.info("call me a hammer the way i SLAM DUNKED THIS MOD (fire emoji)");
	}
}
