package com.thetaciturnone.taccorpstrinkets.item;

import com.thetaciturnone.taccorpstrinkets.TacCorpsTrinkets;
import com.thetaciturnone.taccorpstrinkets.registries.TacItemComponents;
import com.thetaciturnone.taccorpstrinkets.registries.TacItems;
import com.thetaciturnone.taccorpstrinkets.supporter.SupporterUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class QuartziteHammerItem extends BaseHammerItem implements ProjectileItem {

	public QuartziteHammerItem(ToolMaterial material, Item.Settings settings) {
		super(material, settings);
	}

	public static int getVariant(ItemStack stack) {
		HammerVariantComponent hammerVariantComponent = stack.get(TacItemComponents.HAMMER_VARIANT);
		if (hammerVariantComponent != null) { // if not supporter, will return 0 anyway
			return hammerVariantComponent.variant();
		}
		return 0;
	}

	public static void setVariant(ItemStack stack, int variant) {
		HammerVariantComponent hammerVariantComponent = stack.get(TacItemComponents.HAMMER_VARIANT);
		if (hammerVariantComponent != null) {
			stack.set(TacItemComponents.HAMMER_VARIANT, hammerVariantComponent.updateComponent(variant, getSupporterHeldStatus(stack)));
		}
		else {
			stack.set(TacItemComponents.HAMMER_VARIANT, new HammerVariantComponent(variant, true));
		}
	}

	public static boolean getSupporterHeldStatus(ItemStack stack) {
		HammerVariantComponent hammerVariantComponent = stack.get(TacItemComponents.HAMMER_VARIANT);
		if (hammerVariantComponent != null) {
			return hammerVariantComponent.heldBySupporter();
		}
		return false;
	}

	public static void setSupporterHeldStatus(ItemStack stack, boolean heldStatus) {
		HammerVariantComponent hammerVariantComponent = stack.get(TacItemComponents.HAMMER_VARIANT);
		if (hammerVariantComponent != null) {
			stack.set(TacItemComponents.HAMMER_VARIANT, hammerVariantComponent.updateComponent(getVariant(stack), heldStatus));
		}
		else {
			stack.set(TacItemComponents.HAMMER_VARIANT, new HammerVariantComponent(0, heldStatus));
		}
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		if (entity.age % 10 == 0){
			if (stack.contains(TacItemComponents.HAMMER_VARIANT)) { // will not check at all if the supporter stuff hasn't been interacted with
				boolean supporter = SupporterUtils.isPlayerSupporter(entity);
				boolean visibleVariant = getSupporterHeldStatus(stack);
				if (supporter && !visibleVariant) {
					setSupporterHeldStatus(stack, true);
				}
				else if (!supporter && visibleVariant) {
					setSupporterHeldStatus(stack, false);
				}
			}
		}
		super.inventoryTick(stack, world, entity, slot, selected);
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
		if (blockState.isOf(Blocks.SMITHING_TABLE) && Objects.requireNonNull(playerEntity).isSneaking() && SupporterUtils.isPlayerSupporter(playerEntity)) {
			setVariant(stack, cycleVariant(getVariant(stack)));
			setSupporterHeldStatus(stack, true);
			world.playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundEvents.BLOCK_SMITHING_TABLE_USE, SoundCategory.NEUTRAL, 1.0F, 1.2F);
			playerEntity.getItemCooldownManager().set(this, 5);
			return ActionResult.success(world.isClient());
		}
		return super.useOnBlock(context);
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		Text name = getHammerNameText(stack);
		if (name != null){
			tooltip.add(Text.literal(""));
			tooltip.add(name);
		}
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
		int variant = getSupporterHeldStatus(stack) ? getVariant(stack) : 0;
		return switch (variant) {
			case 1 -> "item/dedede_quartzite_hammer";
			case 2 -> "item/pico_pico_quartzite_hammer";
			case 3 -> "item/spamton_quartzite_hammer";
			case 4 -> "item/big_bell_quartzite_hammer";
			default -> "item/quartzite_hammer";
		};
	}

	public static @Nullable Text getHammerNameText(ItemStack stack) {
		if (getSupporterHeldStatus(stack)) {
			int variant = getVariant(stack);
			return switch (variant) {
				case 1 -> Text.literal("Dedede").withColor(0x9f0e20);
				case 2 -> Text.literal("Pico Pico").withColor(0xf85c94);
				case 3 -> Text.literal("[ ").append(Text.literal("BIG").withColor(0xf6e77a)).append(Text.literal(" SHOT").withColor(0xf38af0)).append(Text.literal(" ]"));
				case 4 -> Text.literal("Big Bell").withColor(0xf1c04a);
				default -> null;
			};
		}
		return null;
	}
}
