package com.thetaciturnone.taccorpstrinkets.mixin;

import com.thetaciturnone.taccorpstrinkets.TacCorpsTrinkets;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityNavigation.class)
public class EntityNavigationMixin {
    @Shadow
    @Final
    protected MobEntity entity;

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void tick(CallbackInfo ci) {
        if (entity.hasStatusEffect(TacCorpsTrinkets.STUNNED)) {
            ci.cancel();
        }
    }
}
