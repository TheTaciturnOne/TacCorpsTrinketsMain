package com.thetaciturnone.taccorpstrinkets.mixin;

import com.thetaciturnone.taccorpstrinkets.TacCorpsTrinkets;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({LivingEntity.class, PlayerEntity.class})
public class PlayerEntityFromLivingEntityMixin {
    @Inject(method = "applyDamage", at = @At("HEAD"), cancellable = true)
    protected void applyDamage(DamageSource source, float amount, CallbackInfo callbackInfo) {
        LivingEntity livingEntity = LivingEntity.class.cast(this);
        if (livingEntity.hasStatusEffect(TacCorpsTrinkets.STUNNED)) {
            livingEntity.removeStatusEffect(TacCorpsTrinkets.STUNNED);
            callbackInfo.cancel();
        }
    }

}
