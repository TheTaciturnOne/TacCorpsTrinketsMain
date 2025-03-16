package com.thetaciturnone.taccorpstrinkets.registries;

import net.minecraft.block.Block;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;

public class QuartziteToolMaterial implements ToolMaterial {

	public static final QuartziteToolMaterial QUARTZITE = new QuartziteToolMaterial();

	@Override
	public int getDurability() {
		return 3062;
	}

	@Override
	public float getMiningSpeedMultiplier() {
		return 11.5F;
	}

	@Override
	public float getAttackDamage() {
		return 5.0F;
	}

	@Override
	public TagKey<Block> getInverseTag() {
		return BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
	}

	@Override
	public int getEnchantability() {
		return 15;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return Ingredient.ofItems(TacItems.PRISMATIC_QUARTZ_SHARD);
	}
}
