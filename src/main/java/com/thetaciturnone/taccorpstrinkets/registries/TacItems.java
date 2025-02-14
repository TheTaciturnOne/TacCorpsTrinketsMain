package com.thetaciturnone.taccorpstrinkets.registries;

import com.thetaciturnone.taccorpstrinkets.TacCorpsTrinkets;
import com.thetaciturnone.taccorpstrinkets.item.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class TacItems {

	public static final Item NETHERITE_SLAG = registerItem("netherite_slag",
		new Item(new Item.Settings().group(TacGroup.TACCORP_TRINKETS).fireproof()));
	public static final Item PRISMATIC_QUARTZ_SHARD = registerItem("prismatic_quartz_shard",
		new Item(new Item.Settings().group(TacGroup.TACCORP_TRINKETS)));
	public static final Item QUARTZITE = registerItem("quartzite",
		new Item(new Item.Settings().group(TacGroup.TACCORP_TRINKETS).fireproof()));
	public static final Item MARSHMALLOW = registerItem("marshmallow",
		new Item(new Item.Settings().group(TacGroup.TACCORP_TRINKETS).food(TacFoodItems.MARSHMALLOW_FOOD_ITEM)));
	public static final Item QUARTZITE_HAMMER = registerItem("quartzite_hammer",
		new QuartziteHammerItem(TacToolMaterials.QUARTZITE, 6, -3F,
			new Item.Settings().group(TacGroup.TACCORP_TRINKETS).maxCount(1).rarity(Rarity.RARE).fireproof()));
	public static final Item SHATTERED_QUARTZITE_HAMMER = registerItem("shattered_quartzite_hammer",
		new ShatteredHammerItem(TacToolMaterials.QUARTZITE, 4, -2.7F,
			new Item.Settings().group(TacGroup.TACCORP_TRINKETS).maxCount(1).rarity(Rarity.RARE).fireproof()));
	public static final Item MASK_OF_SILENCE = registerItem("mask_of_silence",
		new SilentMaskItem(new Item.Settings().group(TacGroup.TACCORP_TRINKETS).maxCount(1).rarity(Rarity.RARE).fireproof()));

	public static final Item QUARTZ_CRYSTAL = registerItem("quartz_crystal",
		new BlockItem(TacBlocks.QUARTZ_CRYSTAL, (new Item.Settings()).group(TacGroup.TACCORP_TRINKETS)));
	public static final Item BUDDING_QUARTZ_CRYSTAL = registerItem("budding_quartz_crystal",
		new BlockItem(TacBlocks.BUDDING_QUARTZ_CRYSTAL, (new Item.Settings()).group(TacGroup.TACCORP_TRINKETS)));
	public static final Item QUARTZ_CRYSTAL_CLUSTER = registerItem("quartz_crystal_cluster",
		new BlockItem(TacBlocks.QUARTZ_CRYSTAL_CLUSTER, (new Item.Settings()).group(TacGroup.TACCORP_TRINKETS)));
	public static final Item LARGE_QUARTZ_CRYSTAL_CLUSTER = registerItem("large_quartz_crystal_cluster",
		new BlockItem(TacBlocks.LARGE_QUARTZ_CRYSTAL_CLUSTER, (new Item.Settings()).group(TacGroup.TACCORP_TRINKETS)));
	public static final Item MEDIUM_QUARTZ_CRYSTAL_CLUSTER = registerItem("medium_quartz_crystal_cluster",
		new BlockItem(TacBlocks.MEDIUM_QUARTZ_CRYSTAL_CLUSTER, (new Item.Settings()).group(TacGroup.TACCORP_TRINKETS)));
	public static final Item SMALL_QUARTZ_CRYSTAL_CLUSTER = registerItem("small_quartz_crystal_cluster",
		new BlockItem(TacBlocks.SMALL_QUARTZ_CRYSTAL_CLUSTER, (new Item.Settings()).group(TacGroup.TACCORP_TRINKETS)));
	public static final Item PRISMATIC_QUARTZ_CRYSTAL = registerItem("prismatic_quartz_crystal",
		new BlockItem(TacBlocks.PRISMATIC_QUARTZ_CRYSTAL, (new Item.Settings()).group(TacGroup.TACCORP_TRINKETS)));
	public static final Item QUARTZ_GLASS = registerItem("quartz_glass",
		new BlockItem(TacBlocks.QUARTZ_GLASS, (new Item.Settings()).group(TacGroup.TACCORP_TRINKETS)));
	public static final Item QUARTZ_GLASS_PANE = registerItem("quartz_glass_pane",
		new BlockItem(TacBlocks.QUARTZ_GLASS_PANE, (new Item.Settings()).group(TacGroup.TACCORP_TRINKETS)));
	public static final Item ENGRAVED_QUARTZ = registerItem("engraved_quartz_block",
		new BlockItem(TacBlocks.ENGRAVED_QUARTZ_BLOCK, (new Item.Settings()).group(TacGroup.TACCORP_TRINKETS)));
	public static final Item NETHERITE_FRAME = registerItem("netherite_frame",
		new BlockItem(TacBlocks.NETHERITE_FRAME, (new Item.Settings()).group(TacGroup.TACCORP_TRINKETS).fireproof()));
	public static final Item NETHERITE_STRENGTHENED_QUARTZ_GLASS = registerItem("netherite_strengthened_quartz_glass",
		new BlockItem(TacBlocks.NETHERITE_STRENGTHENED_QUARTZ_GLASS, (new Item.Settings()).group(TacGroup.TACCORP_TRINKETS).fireproof()));
	public static final Item NETHERITE_STRENGTHENED_QUARTZ_GLASS_PANE = registerItem("netherite_strengthened_quartz_glass_pane",
		new BlockItem(TacBlocks.NETHERITE_STRENGTHENED_QUARTZ_GLASS_PANE, (new Item.Settings()).group(TacGroup.TACCORP_TRINKETS).fireproof()));
	public static final Item NETHERITE_STRENGTHENED_QUARTZ_BLOCK = registerItem("netherite_strengthened_quartz_block",
		new BlockItem(TacBlocks.NETHERITE_STRENGTHENED_QUARTZ_BLOCK, (new Item.Settings()).group(TacGroup.TACCORP_TRINKETS).fireproof()));
	public static final Item NETHERITE_STRENGTHENED_QUARTZ_SLAB = registerItem("netherite_strengthened_quartz_slab",
		new BlockItem(TacBlocks.NETHERITE_STRENGTHENED_QUARTZ_SLAB, (new Item.Settings()).group(TacGroup.TACCORP_TRINKETS).fireproof()));
	public static final Item NETHERITE_STRENGTHENED_QUARTZ_STAIRS = registerItem("netherite_strengthened_quartz_stairs",
		new BlockItem(TacBlocks.NETHERITE_STRENGTHENED_QUARTZ_STAIRS, (new Item.Settings()).group(TacGroup.TACCORP_TRINKETS).fireproof()));
	public static final Item NETHERITE_STRENGTHENED_QUARTZ_BRICKS = registerItem("netherite_strengthened_quartz_bricks",
		new BlockItem(TacBlocks.NETHERITE_STRENGTHENED_QUARTZ_BRICKS, (new Item.Settings()).group(TacGroup.TACCORP_TRINKETS).fireproof()));
	public static final Item NETHERITE_STRENGTHENED_CHISELED_QUARTZ_BLOCK = registerItem("netherite_strengthened_chiseled_quartz_block",
		new BlockItem(TacBlocks.NETHERITE_STRENGTHENED_CHISELED_QUARTZ_BLOCK, (new Item.Settings()).group(TacGroup.TACCORP_TRINKETS).fireproof()));
	public static final Item NETHERITE_STRENGTHENED_QUARTZ_PILLAR = registerItem("netherite_strengthened_quartz_pillar",
		new BlockItem(TacBlocks.NETHERITE_STRENGTHENED_QUARTZ_PILLAR, (new Item.Settings()).group(TacGroup.TACCORP_TRINKETS).fireproof()));
	public static final Item NETHERITE_STRENGTHENED_ENGRAVED_QUARTZ_BLOCK = registerItem("netherite_strengthened_engraved_quartz_block",
		new BlockItem(TacBlocks.NETHERITE_STRENGTHENED_ENGRAVED_QUARTZ_BLOCK, (new Item.Settings()).group(TacGroup.TACCORP_TRINKETS).fireproof()));
	public static final Item TAC_PLUSHIE = registerItem("tac_plushie",
		new TacPlushieItem(TacBlocks.TAC_PLUSHIE, (new Item.Settings()).group(TacGroup.TACCORP_TRINKETS)));
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(TacCorpsTrinkets.MOD_ID, name), item);
    }

	private static void registerBlockItem(String name, Block block, ItemGroup tab) {
		Registry.register(Registry.ITEM, new Identifier(TacCorpsTrinkets.MOD_ID, name),
			new BlockItem(block, new FabricItemSettings().group(tab)));
	}

    public static void registerModItems() {
        TacCorpsTrinkets.LOGGER.debug("Letting souls bloom"+ TacCorpsTrinkets.MOD_ID);
    }
}
