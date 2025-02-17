package com.thetaciturnone.taccorpstrinkets.mixin;

import net.minecraft.client.gui.screen.SplashTextRenderer;
import net.minecraft.client.resource.SplashTextResourceSupplier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(SplashTextResourceSupplier.class)
public class SplashTextResourceSupplierMixin {

	@Shadow
	@Final
	private List<String> splashTexts;

	@Inject(method = "get", at = @At("HEAD"))
	private void tacCorp$fireEmojiSplashText(CallbackInfoReturnable<SplashTextRenderer> cir) {
		if (!this.splashTexts.isEmpty()) {
			this.splashTexts.add(this.splashTexts.size() - 1, "call me a hammer the way i SLAM DUNKED THIS MOD (fire emoji)");
		}
	} // adds the splash text at the ending index to prevent it from being the most recent element and thus appearing on the initial title screen every time
}
