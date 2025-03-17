package com.thetaciturnone.taccorpstrinkets.item;

import com.thetaciturnone.taccorpstrinkets.TacCorpsTrinkets;
import com.thetaciturnone.taccorpstrinkets.entity.ThrownHammerEntity;
import com.thetaciturnone.taccorpstrinkets.registries.TacBlocks;
import com.thetaciturnone.taccorpstrinkets.registries.TacEnchantmentEffects;
import com.thetaciturnone.taccorpstrinkets.registries.TacItemComponents;
import com.thetaciturnone.taccorpstrinkets.registries.TacItems;
import com.thetaciturnone.taccorpstrinkets.utils.TacDamage;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.Tameable;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

import java.util.List;
import java.util.Objects;

public class QuartziteHammerItem extends BaseHammerItem implements ProjectileItem {

	public QuartziteHammerItem(ToolMaterial material, Item.Settings settings) {
		super(material, settings);
	}

	public static int getVariant(ItemStack stack) {
		return stack.getOrDefault(TacItemComponents.HAMMER_VARIANT, 0);
	}

	public static void setVariant(ItemStack stack, int variant) {
		stack.set(TacItemComponents.HAMMER_VARIANT, variant);
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		World world = context.getWorld();
		BlockPos blockPos = context.getBlockPos();
		BlockState blockState = world.getBlockState(blockPos);
		PlayerEntity playerEntity = context.getPlayer();
		ItemStack stack = context.getStack();
		if (blockState.isIn(BlockTags.ANVIL) && Objects.requireNonNull(playerEntity).isSneaking()) {
			ItemStack shatteredHammer = new ItemStack(TacItems.SHATTERED_QUARTZITE_HAMMER);
			if (stack.getComponents() != null) { // shattering a hammer will carry over item data like name, durability, and enchantments
				shatteredHammer.applyChanges(stack.getComponentChanges()); // preserves the shattered hammer's default components like damage attributes :p
			}
			playerEntity.setStackInHand(playerEntity.getActiveHand(), shatteredHammer);
			world.playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), TacCorpsTrinkets.HAMMER_SHATTER, SoundCategory.NEUTRAL, 1.0F, 1.0F);
			return ActionResult.success(world.isClient());
		}
		if (blockState.isOf(Blocks.SMITHING_TABLE) && Objects.requireNonNull(playerEntity).isSneaking() && playerEntity.getUuidAsString().equals("8333a40b-d46b-4a47-8081-2b721b45b162")) {
			setVariant(stack, cycleVariant(getVariant(stack)));
			world.playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundEvents.BLOCK_SMITHING_TABLE_USE, SoundCategory.NEUTRAL, 1.0F, 1.2F);
			playerEntity.getItemCooldownManager().set(this, 5);
			return ActionResult.success(world.isClient());
		}
		return super.useOnBlock(context);
	}


	public static int cycleVariant(int current) {
		if (current < 4) {
			return current + 1;
		}
		return 0;
	}

	public static Identifier getHammerModelIdentifier(ItemStack stack, boolean isHandheld) {
		String modelString = isHandheld ? getHammerModelString(stack) + "_handheld" : getHammerModelString(stack);
		return TacCorpsTrinkets.id(modelString);
	}

	public static String getHammerModelString(ItemStack stack) {
		int variant = getVariant(stack);
		return switch (variant) {
			case 1 -> "item/dedede_quartzite_hammer";
			case 2 -> "item/pico_pico_quartzite_hammer";
			case 3 -> "item/hextech_quartzite_hammer";
			case 4 -> "item/spamton_quartzite_hammer";
			default -> "item/quartzite_hammer";
		};
	}

}
