package com.thetaciturnone.taccorpstrinkets.registries;

import com.thetaciturnone.taccorpstrinkets.TacCorpsTrinkets;
import com.thetaciturnone.taccorpstrinkets.item.*;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;

public class TacItems {

	public static final Item NETHERITE_SLAG = registerItem("netherite_slag",
		new Item(new Item.Settings().fireproof()));
	public static final Item PRISMATIC_QUARTZ_SHARD = registerItem("prismatic_quartz_shard",
		new Item(new Item.Settings()));
	public static final Item QUARTZITE = registerItem("quartzite",
		new Item(new Item.Settings().fireproof()));
	public static final Item MARSHMALLOW = registerItem("marshmallow",
		new Item(new Item.Settings().food(TacFoodItems.MARSHMALLOW_FOOD_ITEM)));
	public static final Item QUARTZITE_HAMMER = registerItem("quartzite_hammer",
		new QuartziteHammerItem(TacToolMaterials.QUARTZITE, 6, -3F,
			new Item.Settings().maxCount(1).rarity(Rarity.RARE).fireproof()));
	public static final Item SHATTERED_QUARTZITE_HAMMER = registerItem("shattered_quartzite_hammer",
		new ShatteredHammerItem(TacToolMaterials.QUARTZITE, 4, -2.7F,
			new Item.Settings().maxCount(1).rarity(Rarity.RARE).fireproof()));
	public static final Item MASK_OF_SILENCE = registerItem("mask_of_silence",
		new SilentMaskItem(new Item.Settings().maxCount(1).rarity(Rarity.RARE).fireproof()));

	// Item Group
	public static final ItemGroup TACCORP_TRINKETS = Registry.register(Registries.ITEM_GROUP, TacCorpsTrinkets.id("taccorp_trinkets"),
		FabricItemGroup.builder().displayName(Text.translatable("itemgroup.taccorpstrinkets.taccorp_trinkets"))
			.icon(() -> new ItemStack(TacBlocks.NETHERITE_STRENGTHENED_QUARTZ_BLOCK)).entries((displayContext, entries) -> {
				entries.add(NETHERITE_SLAG);
				entries.add(PRISMATIC_QUARTZ_SHARD);
				entries.add(QUARTZITE);
				entries.add(MARSHMALLOW);
				entries.add(QUARTZITE_HAMMER);
				entries.add(SHATTERED_QUARTZITE_HAMMER);
				entries.add(MASK_OF_SILENCE);
				entries.add(TacBlocks.QUARTZ_CRYSTAL);
				entries.add(TacBlocks.BUDDING_QUARTZ_CRYSTAL);
				entries.add(TacBlocks.QUARTZ_CRYSTAL_CLUSTER);
				entries.add(TacBlocks.LARGE_QUARTZ_CRYSTAL_CLUSTER);
				entries.add(TacBlocks.MEDIUM_QUARTZ_CRYSTAL_CLUSTER);
				entries.add(TacBlocks.SMALL_QUARTZ_CRYSTAL_CLUSTER);
				entries.add(TacBlocks.PRISMATIC_QUARTZ_CRYSTAL);
				entries.add(TacBlocks.QUARTZ_GLASS);
				entries.add(TacBlocks.QUARTZ_GLASS_PANE);
				entries.add(TacBlocks.ENGRAVED_QUARTZ_BLOCK);
				entries.add(TacBlocks.NETHERITE_FRAME);
				entries.add(TacBlocks.NETHERITE_STRENGTHENED_QUARTZ_GLASS);
				entries.add(TacBlocks.NETHERITE_STRENGTHENED_QUARTZ_GLASS_PANE);
				entries.add(TacBlocks.NETHERITE_STRENGTHENED_QUARTZ_BLOCK);
				entries.add(TacBlocks.NETHERITE_STRENGTHENED_QUARTZ_SLAB);
				entries.add(TacBlocks.NETHERITE_STRENGTHENED_QUARTZ_STAIRS);
				entries.add(TacBlocks.NETHERITE_STRENGTHENED_QUARTZ_BRICKS);
				entries.add(TacBlocks.NETHERITE_STRENGTHENED_CHISELED_QUARTZ_BLOCK);
				entries.add(TacBlocks.NETHERITE_STRENGTHENED_QUARTZ_PILLAR);
				entries.add(TacBlocks.NETHERITE_STRENGTHENED_ENGRAVED_QUARTZ_BLOCK);
				entries.add(TacBlocks.TAC_PLUSHIE);
			}).build()
		);


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, TacCorpsTrinkets.id(name), item);
    }

    public static void registerModItems() {

    }
}
