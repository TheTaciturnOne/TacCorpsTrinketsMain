package com.thetaciturnone.taccorpstrinkets.registries;

import com.thetaciturnone.taccorpstrinkets.TacCorpsTrinkets;
import com.thetaciturnone.taccorpstrinkets.blocks.*;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;

public class TacBlocks {

	public static final Block QUARTZ_CRYSTAL = registerBlock("quartz_crystal",
		new AmethystBlock(QuiltBlockSettings.of(Material.AMETHYST)
			.strength(1.5F).sounds(BlockSoundGroup.AMETHYST_BLOCK).requiresTool()), TacGroup.TACCORP_TRINKETS);
	public static final Block QUARTZ_CRYSTAL_CLUSTER = registerBlock("quartz_crystal_cluster",
		new AmethystClusterBlock(7, 3, AbstractBlock.Settings.of(Material.AMETHYST).nonOpaque().requiresTool().ticksRandomly().sounds(BlockSoundGroup.AMETHYST_CLUSTER).strength(1.5F).luminance((state) -> {
			return 5;
		})), TacGroup.TACCORP_TRINKETS);
	public static final Block LARGE_QUARTZ_CRYSTAL_CLUSTER = registerBlock("large_quartz_crystal_cluster",
		new AmethystClusterBlock(5, 3, AbstractBlock.Settings.of(Material.AMETHYST).nonOpaque().requiresTool().ticksRandomly().sounds(BlockSoundGroup.AMETHYST_CLUSTER).strength(1.5F).luminance((state) -> {
			return 5;
		})), TacGroup.TACCORP_TRINKETS);
	public static final Block MEDIUM_QUARTZ_CRYSTAL_CLUSTER = registerBlock("medium_quartz_crystal_cluster",
		new AmethystClusterBlock(4, 3, AbstractBlock.Settings.of(Material.AMETHYST).nonOpaque().requiresTool().ticksRandomly().sounds(BlockSoundGroup.AMETHYST_CLUSTER).strength(1.5F).luminance((state) -> {
			return 5;
		})), TacGroup.TACCORP_TRINKETS);
	public static final Block SMALL_QUARTZ_CRYSTAL_CLUSTER = registerBlock("small_quartz_crystal_cluster",
		new AmethystClusterBlock(3, 3, AbstractBlock.Settings.of(Material.AMETHYST).nonOpaque().requiresTool().ticksRandomly().sounds(BlockSoundGroup.AMETHYST_CLUSTER).strength(1.5F).luminance((state) -> {
			return 5;
		})), TacGroup.TACCORP_TRINKETS);
	public static final Block BUDDING_QUARTZ_CRYSTAL = registerBlock("budding_quartz_crystal",
		new BuddingQuartzBlock(QuiltBlockSettings.of(Material.AMETHYST)
			.strength(1.5F).sounds(BlockSoundGroup.AMETHYST_BLOCK).ticksRandomly().requiresTool()), TacGroup.TACCORP_TRINKETS);
	public static final Block ENGRAVED_QUARTZ_BLOCK = registerBlock("engraved_quartz_block",
		new Block(QuiltBlockSettings.of(Material.STONE)
			.strength(0.8F).sounds(BlockSoundGroup.STONE).requiresTool()), TacGroup.TACCORP_TRINKETS);
	public static final Block QUARTZ_GLASS = registerBlock("quartz_glass",
		new QuartzGlassBlock(FabricBlockSettings.of(Material.GLASS)
			.strength(0.3F).sounds(BlockSoundGroup.GLASS).nonOpaque().requiresTool()), TacGroup.TACCORP_TRINKETS);
	public static final Block QUARTZ_GLASS_PANE = registerBlock("quartz_glass_pane",
		new QuartzGlassPaneBlock(FabricBlockSettings.of(Material.GLASS)
			.strength(0.3F).sounds(BlockSoundGroup.GLASS).requiresTool()), TacGroup.TACCORP_TRINKETS);
	public static final Block NETHERITE_FRAME = registerBlock("netherite_frame",
		new NetheriteFrameBlock(QuiltBlockSettings.of(Material.METAL)
			.strength(20F, 1200.0F).sounds(BlockSoundGroup.NETHERITE).requiresTool()), TacGroup.TACCORP_TRINKETS);
	public static final Block NETHERITE_STRENGTHENED_QUARTZ_GLASS = registerBlock("netherite_strengthened_quartz_glass",
		new GlassBlock(QuiltBlockSettings.of(Material.GLASS)
			.strength(0.3F, 1200.0F).sounds(BlockSoundGroup.GLASS).nonOpaque().requiresTool()), TacGroup.TACCORP_TRINKETS);
	public static final Block NETHERITE_STRENGTHENED_QUARTZ_GLASS_PANE = registerBlock("netherite_strengthened_quartz_glass_pane",
		new PaneBlock(QuiltBlockSettings.of(Material.GLASS)
			.strength(0.3F, 1200.0F).sounds(BlockSoundGroup.GLASS).requiresTool()), TacGroup.TACCORP_TRINKETS);
	public static final Block NETHERITE_STRENGTHENED_QUARTZ_BLOCK = registerBlock("netherite_strengthened_quartz_block",
		new Block(QuiltBlockSettings.of(Material.METAL)
			.strength(1.2F, 1200.0F).sounds(BlockSoundGroup.NETHERITE).requiresTool()), TacGroup.TACCORP_TRINKETS);
	public static final Block NETHERITE_STRENGTHENED_QUARTZ_SLAB = registerBlock("netherite_strengthened_quartz_slab",
		new SlabBlock(QuiltBlockSettings.of(Material.METAL)
			.strength(1.2F, 1200.0F).sounds(BlockSoundGroup.NETHERITE).requiresTool()), TacGroup.TACCORP_TRINKETS);
	public static final Block NETHERITE_STRENGTHENED_QUARTZ_STAIRS = registerBlock("netherite_strengthened_quartz_stairs",
		new StairsBlock(TacBlocks.NETHERITE_STRENGTHENED_QUARTZ_BLOCK.getDefaultState(), FabricBlockSettings.of(Material.METAL)
			.strength(1.2F, 1200.0F).sounds(BlockSoundGroup.NETHERITE).requiresTool()), TacGroup.TACCORP_TRINKETS);
	public static final Block NETHERITE_STRENGTHENED_QUARTZ_BRICKS = registerBlock("netherite_strengthened_quartz_bricks",
		new Block(QuiltBlockSettings.of(Material.METAL)
			.strength(1.2F, 1200.0F).sounds(BlockSoundGroup.NETHERITE).requiresTool()), TacGroup.TACCORP_TRINKETS);
	public static final Block NETHERITE_STRENGTHENED_CHISELED_QUARTZ_BLOCK = registerBlock("netherite_strengthened_chiseled_quartz_block",
		new Block(QuiltBlockSettings.of(Material.METAL)
			.strength(1.2F, 1200.0F).sounds(BlockSoundGroup.NETHERITE).requiresTool()), TacGroup.TACCORP_TRINKETS);
	public static final Block NETHERITE_STRENGTHENED_QUARTZ_PILLAR = registerBlock("netherite_strengthened_quartz_pillar",
		new PillarBlock(QuiltBlockSettings.of(Material.METAL)
			.strength(1.2F, 1200.0F).sounds(BlockSoundGroup.NETHERITE).requiresTool()), TacGroup.TACCORP_TRINKETS);
	public static final Block NETHERITE_STRENGTHENED_ENGRAVED_QUARTZ_BLOCK = registerBlock("netherite_strengthened_engraved_quartz_block",
		new Block(QuiltBlockSettings.of(Material.METAL)
			.strength(1.2F, 1200.0F).sounds(BlockSoundGroup.NETHERITE).requiresTool()), TacGroup.TACCORP_TRINKETS);
	public static final Block TAC_PLUSHIE = registerBlock("tac_plushie",
		new TacPlushieBlock(QuiltBlockSettings.of(Material.WOOL)
			.strength(0.8F).sounds(BlockSoundGroup.WOOL)), TacGroup.TACCORP_TRINKETS);

	private static Block registerBlock(String name, Block block, ItemGroup tab ) {
		return Registry.register(Registry.BLOCK, new Identifier(TacCorpsTrinkets.MOD_ID, name), block);
	}


	private static void registerBlockItem(String name, Block block, ItemGroup tab) {
		Registry.register(Registry.ITEM, new Identifier(TacCorpsTrinkets.MOD_ID, name),
			new BlockItem(block, new QuiltItemSettings().group(tab)));
	}

	private static Block registerBlockWithoutItem(String name, Block block) {
		return Registry.register(Registry.BLOCK, new Identifier(TacCorpsTrinkets.MOD_ID, name), block);
	}

	public static void registerModBlocks () {
		TacCorpsTrinkets.LOGGER.debug("Getting a little spirity"+ TacCorpsTrinkets.MOD_ID);
	}
}
