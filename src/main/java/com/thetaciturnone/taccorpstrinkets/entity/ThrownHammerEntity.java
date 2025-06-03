package com.thetaciturnone.taccorpstrinkets.entity;

import com.thetaciturnone.taccorpstrinkets.TacCorpsTrinkets;
import com.thetaciturnone.taccorpstrinkets.registries.TacEntities;
import com.thetaciturnone.taccorpstrinkets.registries.TacItems;
import com.thetaciturnone.taccorpstrinkets.utils.TacDamage;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ThrownHammerEntity extends PersistentProjectileEntity {
	public static final TrackedData<ItemStack> THROWN_ITEM = DataTracker.registerData(ThrownHammerEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);
	private boolean dealtDamage;
	public int returnTimer;
	public int stackSlot = -1;

	public ThrownHammerEntity(EntityType<? extends ThrownHammerEntity> entityType, World world) {
		super(entityType, world);
	}

	public ThrownHammerEntity(World world, LivingEntity owner, ItemStack stack) {
		super(TacEntities.THROWN_HAMMER, owner, world, stack, null);
		this.dataTracker.set(THROWN_ITEM,  stack);
		if (owner instanceof PlayerEntity playerEntity) {
			int slot = playerEntity.getInventory().getSlotWithStack(stack); // prioritizes main hand
			if (slot == -1 && ItemStack.areItemsAndComponentsEqual(playerEntity.getOffHandStack(), stack)) {
				slot = PlayerInventory.OFF_HAND_SLOT;
			}
			this.stackSlot = slot; // preserves the stack slot rahhh!!!
		}
	}

	public ThrownHammerEntity(World world, double x, double y, double z, ItemStack stack) {
		super(TacEntities.THROWN_HAMMER, x, y, z, world, stack, stack);
		this.dataTracker.set(THROWN_ITEM, stack);
	}

	public ItemStack getItemStack() {
		return this.getDataTracker().get(THROWN_ITEM);
	}

	public ItemStack getDefaultItemStack() {
		return new ItemStack(TacItems.QUARTZITE_HAMMER);
	}

	public ItemStack getItem() {
		return this.getItemStack();
	}

	public void setItemStack(ItemStack stack) {
		this.getDataTracker().set(THROWN_ITEM, stack);
	}

	@Override
	protected void initDataTracker(DataTracker.Builder builder) {
		super.initDataTracker(builder);
		builder.add(THROWN_ITEM, this.getDefaultItemStack());
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
				if (this.getWorld().isClient()) {
					this.lastRenderY = this.getY();
				}

				this.setVelocity(this.getVelocity().multiply(0.9).add(vec3d.normalize().multiply(0.25)));
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

	@Override
	protected void onBlockHit(BlockHitResult blockHitResult) {
		BlockState blockState = this.getWorld().getBlockState(blockHitResult.getBlockPos());
		blockState.onProjectileHit(this.getWorld(), blockState, blockHitResult, this);
		Vec3d vec3d = blockHitResult.getPos().subtract(this.getX(), this.getY(), this.getZ());
		this.setVelocity(vec3d);
		ItemStack itemStack = this.getWeaponStack();
		World var5 = this.getWorld();
		if (var5 instanceof ServerWorld serverWorld) {
			if (itemStack != null) {
				this.onBlockHitEnchantmentEffects(serverWorld, blockHitResult, itemStack);
			}
		}
		Vec3d vec3d2 = vec3d.normalize().multiply(0.05);
		this.setPos(this.getX() - vec3d2.x, this.getY() - vec3d2.y, this.getZ() - vec3d2.z);
		this.playSound(this.getSound(), 4.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
		this.inGround = true;
		this.shake = 7;
		this.setCritical(false);
	}

	protected void onEntityHit(EntityHitResult entityHitResult) {
		Entity entity = entityHitResult.getEntity();
		Entity owner = this.getOwner();
		World world = this.getWorld();

		float f = 10.0F;
		DamageSource damageSource = TacDamage.create(world, TacDamage.HAMMER_POWERSLAM, this, owner == null ? this : owner);
		this.dealtDamage = true;
		if (world instanceof ServerWorld serverWorld) {
			f = EnchantmentHelper.getDamage(serverWorld, this.getItemStack(), entity, damageSource, f);
		}

		if (entity.damage(damageSource, f)) {
			if (entity.getType() == EntityType.ENDERMAN) {
				return;
			}

			if (world instanceof ServerWorld serverWorld) {
				EnchantmentHelper.onTargetDamaged(serverWorld, entity, damageSource, this.getWeaponStack());
			}

			if (entity instanceof LivingEntity target) {
				this.knockback(target, damageSource);
				this.onHit(target);
			}
		}

		this.setVelocity(this.getVelocity().multiply(-0.01, -0.1, -0.01));
		this.playSound(TacCorpsTrinkets.HAMMER_SLAMMED, 4.0F, 1.0F);
	}
	protected boolean tryPickup(PlayerEntity player) {
		if (this.isNoClip() && this.isOwner(player) && stackSlot != -1) { // if hammer is returning and owned by the player and stack slot is valid slot
			if (player.getInventory().getStack(stackSlot).isEmpty()) { // checks main inventory first
				player.getInventory().setStack(stackSlot, this.asItemStack());
				return true;
			}
			if (stackSlot == PlayerInventory.OFF_HAND_SLOT && player.getOffHandStack().isEmpty()) { // checks offhand if slot not found in main inventory
				player.getInventory().setStack(PlayerInventory.OFF_HAND_SLOT, this.asItemStack());
				return true;
			}
		} // otherwise defaults to normal insertion behavior
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

	@Override
	protected void onHit(LivingEntity target) {
		super.onHit(target);
		target.addStatusEffect(new StatusEffectInstance(TacCorpsTrinkets.STUNNED, 120, 0));
	}

	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		this.dealtDamage = nbt.getBoolean("DealtDamage");
		this.stackSlot = nbt.getInt("StackSlot");
	}

	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putBoolean("DealtDamage", this.dealtDamage);
		nbt.putInt("StackSlot", this.stackSlot);
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

