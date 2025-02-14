package com.thetaciturnone.taccorpstrinkets.entity;

import com.thetaciturnone.taccorpstrinkets.TacCorpsTrinkets;
import com.thetaciturnone.taccorpstrinkets.registries.TacEntities;
import com.thetaciturnone.taccorpstrinkets.utils.TacDamage;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ThrownHammerEntity extends PersistentProjectileEntity {
	public static final TrackedData<NbtCompound> THROWN_ITEM = DataTracker.registerData(ThrownHammerEntity.class, TrackedDataHandlerRegistry.NBT_COMPOUND);
	private boolean dealtDamage;
	public int returnTimer;

	public ThrownHammerEntity(EntityType<? extends ThrownHammerEntity> entityType, World world) {
		super(entityType, world);
	}

	public ThrownHammerEntity(World world, LivingEntity owner, ItemStack stack) {
		super(TacEntities.THROWN_HAMMER, owner, world);
		this.dataTracker.set(THROWN_ITEM, stack.copy().writeNbt(new NbtCompound()));
	}

	public ItemStack getItemStack() {
		return ItemStack.fromNbt(this.getDataTracker().get(THROWN_ITEM));
	}


	public ItemStack getItem() {
		return this.getItemStack();
	}

	public void setItemStack(ItemStack stack) {
		this.getDataTracker().set(THROWN_ITEM, stack.writeNbt(new NbtCompound()));
	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(THROWN_ITEM, new NbtCompound());
	}

	public void tick() {
		if (this.inGroundTime > 3) {
			this.dealtDamage = true;
		}

		Entity entity = this.getOwner();
		if ((this.dealtDamage || this.isNoClip()) && entity != null) {
				this.setNoClip(true);
				Vec3d vec3d = entity.getEyePos().subtract(this.getPos());
				this.setPos(this.getX(), this.getY() + vec3d.y * 0.015, this.getZ());
				if (this.world.isClient) {
					this.lastRenderY = this.getY();
				}

				double d = 0.05;
				this.setVelocity(this.getVelocity().multiply(0.9D).add(vec3d.normalize().multiply(0.25D)));
				if (this.returnTimer == 0) {
					this.playSound(TacCorpsTrinkets.HAMMER_THROW, 10.0F, 1.0F);
				}

				++this.returnTimer;
			}
			super.tick();
		}

	private boolean isOwnerAlive() {
		Entity entity = this.getOwner();
		if (entity != null && entity.isAlive()) {
			return !(entity instanceof ServerPlayerEntity) || !entity.isSpectator();
		} else {
			return false;
		}
	}

	protected ItemStack asItemStack() {
		return this.getItemStack().copy();
	}

	@Nullable
	protected EntityHitResult getEntityCollision(Vec3d currentPosition, Vec3d nextPosition) {
		return this.dealtDamage ? null : super.getEntityCollision(currentPosition, nextPosition);
	}

	protected void onEntityHit(EntityHitResult entityHitResult) {
		Entity entity = entityHitResult.getEntity();
		float f = 8.0F;
		if (entity instanceof LivingEntity livingEntity) {
			f += EnchantmentHelper.getAttackDamage(this.getItem(), livingEntity.getGroup());
			((LivingEntity) entity).takeKnockback(1.5, entity.getX() - this.getX(), entity.getZ() - this.getZ() );
		}

		Entity entity2 = this.getOwner();
		DamageSource damageSource = TacDamage.hammer_throw(this, entity2 == null ? this : entity2);
		this.dealtDamage = true;
		SoundEvent soundEvent = TacCorpsTrinkets.HAMMER_SLAMMED;
		if (entity.damage(damageSource, f)) {
			if (entity.getType() == EntityType.ENDERMAN) {
				return;
			}

			if (entity instanceof LivingEntity) {
				LivingEntity livingEntity2 = (LivingEntity)entity;
				if (entity2 instanceof LivingEntity) {
					EnchantmentHelper.onUserDamaged(livingEntity2, entity2);
					EnchantmentHelper.onTargetDamaged((LivingEntity)entity2, livingEntity2);
				}

				this.onHit(livingEntity2);
			}
		}

		this.setVelocity(this.getVelocity().multiply(-0.01, -0.1, -0.01));
		float g = 1.0F;

		this.playSound(soundEvent, g, 1.0F);
	}
	protected boolean tryPickup(PlayerEntity player) {
		return super.tryPickup(player) || this.isNoClip() && this.isOwner(player) && player.getInventory().insertStack(this.asItemStack());
	}

	protected SoundEvent getHitSound() {
		return TacCorpsTrinkets.HAMMER_SLAMMED;
	}

	public void onPlayerCollision(PlayerEntity player) {
		if (this.isOwner(player) || this.getOwner() == null) {
			super.onPlayerCollision(player);
		}

	}

	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		if (nbt.contains("Hammer", 10)) {
			this.setItemStack(ItemStack.fromNbt(nbt.getCompound("Hammer")));
		}

		this.dealtDamage = nbt.getBoolean("DealtDamage");
	}

	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.put("Hammer", this.getDataTracker().get(THROWN_ITEM));
		nbt.putBoolean("DealtDamage", this.dealtDamage);
	}

	public void age() {
		if (this.pickupType != PickupPermission.ALLOWED) {
			super.age();
		}

	}

	public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
		return true;
	}

	@Environment(value= EnvType.CLIENT)
	public float getAgeException() {
		if (!this.inGround) {
			return age;
		}
		return 1.0f;
	}
}

