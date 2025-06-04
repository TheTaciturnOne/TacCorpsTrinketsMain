package com.thetaciturnone.taccorpstrinkets.component;

import com.thetaciturnone.taccorpstrinkets.registries.TacEntityComponents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class HammerUserComponent implements AutoSyncedComponent, CommonTickingComponent {

	private final PlayerEntity playerEntity;
	private boolean boosted;
	private int boostedTicks;

	public HammerUserComponent(PlayerEntity playerEntity) {
		this.playerEntity = playerEntity;
	}

	@Override
	public void readFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup wrapperLookup) {
		boosted = nbt.getBoolean("Boosted");
		boostedTicks = nbt.getInt("BoostedTicks");
	}

	@Override
	public void writeToNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup wrapperLookup) {
		nbt.putBoolean("Boosted", boosted);
		nbt.putInt("BoostedTicks", boostedTicks);
	}

	@Override
	public void tick() {
		if (boostedTicks > 0) {
			boostedTicks--;
		}
		if (boosted) {
			if (boostedTicks == 0 && (playerEntity.isOnGround() || playerEntity.isTouchingWater())) {
				setBoosted(false);
			}
		}
	}

	public boolean hasBoosted() {
		return boosted;
	}

	public void setBoosted(boolean value) {
		boosted = value;
		boostedTicks = value ? 10 : 0;
		sync();
	}

	public void sync() {
		TacEntityComponents.HAMMER_USER.sync(playerEntity);
	}
}
