package com.thetaciturnone.taccorpstrinkets.mixin;

import net.minecraft.client.gui.screen.SplashTextRenderer;
import net.minecraft.client.resource.SplashTextResourceSupplier;
import net.minecraft.util.math.random.Random;
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

	@Shadow
	@Final
	private static Random RANDOM;

	@Inject(method = "get", at = @At("HEAD"))
	private void tacCorp$customSplashTexts(CallbackInfoReturnable<SplashTextRenderer> cir) {
		if (!this.splashTexts.isEmpty()) {
			this.splashTexts.add(RANDOM.nextInt(this.splashTexts.size()), "call me a hammer the way i SLAM DUNKED THIS MOD (fire emoji)");
			this.splashTexts.add(RANDOM.nextInt(this.splashTexts.size()), "TacCorp: A company you can trust!");
			this.splashTexts.add(RANDOM.nextInt(this.splashTexts.size()), "meow :3");
		}
	} // adds the splash text at the ending index to prevent it from being the most recent element and thus appearing on the initial title screen every time
}
