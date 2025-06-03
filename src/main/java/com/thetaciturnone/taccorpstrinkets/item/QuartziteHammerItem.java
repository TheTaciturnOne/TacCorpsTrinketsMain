package com.thetaciturnone.taccorpstrinkets.item;

import com.thetaciturnone.taccorpstrinkets.TacCorpsTrinkets;
import com.thetaciturnone.taccorpstrinkets.registries.TacItemComponents;
import com.thetaciturnone.taccorpstrinkets.registries.TacItems;
import com.thetaciturnone.taccorpstrinkets.supporter.SupporterUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
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
		return stack.getOrDefault(TacItemComponents.HAMMER_VARIANT, 0);
	}

	public static void setVariant(ItemStack stack, int variant) {
		stack.set(TacItemComponents.HAMMER_VARIANT, variant);
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		if (entity.age % 20 == 0){
			if (entity instanceof PlayerEntity player){
				if (!SupporterUtils.isPlayerSupporter(player)){
					setVariant(stack, 0);
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

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		Text name = getHammerNameText(stack);
		if (name != null){
			tooltip.add(Text.literal(""));
			tooltip.add(name);
		}
	}

	public static String getHammerModelString(ItemStack stack) {
		int variant = getVariant(stack);
		return switch (variant) {
			case 1 -> "item/dedede_quartzite_hammer";
			case 2 -> "item/pico_pico_quartzite_hammer";
			case 3 -> "item/spamton_quartzite_hammer";
			case 4 -> "item/big_bell_quartzite_hammer";
			default -> "item/quartzite_hammer";
		};
	}

	public static @Nullable Text getHammerNameText(ItemStack stack) {
		int variant = getVariant(stack);
		return switch (variant) {
			case 1 -> Text.literal("Dedede").withColor(0x9f0e20);
			case 2 -> Text.literal("Pico Pico").withColor(0xf85c94);
			case 3 -> Text.literal("[ ").append(Text.literal("BIG").withColor(0xf6e77a)).append(Text.literal(" SHOT").withColor(0xf38af0)).append(Text.literal(" ]"));
			case 4 -> Text.literal("Big Bell").withColor(0xf1c04a);
			default -> null;
		};
	}
}
