package com.thetaciturnone.taccorpstrinkets.registries;

import com.thetaciturnone.taccorpstrinkets.TacCorpsTrinkets;
import com.thetaciturnone.taccorpstrinkets.blocks.*;
import com.thetaciturnone.taccorpstrinkets.item.TacPlushieItem;
import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;

import java.util.function.ToIntFunction;

public class TacBlocks {

	public static final Block QUARTZ_CRYSTAL = registerBlock("quartz_crystal",
		new QuartzCrystalBlock(AbstractBlock.Settings.copy(Blocks.AMETHYST_BLOCK)
			.strength(1.5F).sounds(
				TacSoundGroups.QUARTZ_CRYSTAL).requiresTool()), false);
	public static final Block QUARTZ_CRYSTAL_CLUSTER = registerBlock("quartz_crystal_cluster",
		new QuartzClusterBlock(7, 3, AbstractBlock.Settings.copy(Blocks.AMETHYST_BLOCK).nonOpaque()
			.requiresTool().ticksRandomly().sounds(TacSoundGroups.QUARTZ_CRYSTAL).strength(1.5F)
			.pistonBehavior(PistonBehavior.DESTROY).luminance((state) -> 5)), false);
	public static final Block LARGE_QUARTZ_CRYSTAL_CLUSTER = registerBlock("large_quartz_crystal_cluster",
		new QuartzClusterBlock(5, 3, AbstractBlock.Settings.copy(Blocks.AMETHYST_BLOCK).nonOpaque()
			.requiresTool().ticksRandomly().sounds(TacSoundGroups.QUARTZ_CRYSTAL).strength(1.5F).
			pistonBehavior(PistonBehavior.DESTROY).luminance((state) -> 5)), false);
	public static final Block MEDIUM_QUARTZ_CRYSTAL_CLUSTER = registerBlock("medium_quartz_crystal_cluster",
		new QuartzClusterBlock(4, 3, AbstractBlock.Settings.copy(Blocks.AMETHYST_BLOCK).nonOpaque()
			.requiresTool().ticksRandomly().sounds(TacSoundGroups.QUARTZ_CRYSTAL).strength(1.5F)
			.pistonBehavior(PistonBehavior.DESTROY).luminance((state) -> 5)), false);
	public static final Block SMALL_QUARTZ_CRYSTAL_CLUSTER = registerBlock("small_quartz_crystal_cluster",
		new QuartzClusterBlock(3, 3, AbstractBlock.Settings.copy(Blocks.AMETHYST_BLOCK).nonOpaque()
			.requiresTool().ticksRandomly().sounds(TacSoundGroups.QUARTZ_CRYSTAL).strength(1.5F)
			.pistonBehavior(PistonBehavior.DESTROY).luminance((state) -> 5)), false);
	public static final Block BUDDING_QUARTZ_CRYSTAL = registerBlock("budding_quartz_crystal",
		new BuddingQuartzBlock(AbstractBlock.Settings.copy(Blocks.AMETHYST_BLOCK)
			.strength(1.5F).pistonBehavior(PistonBehavior.DESTROY).sounds(TacSoundGroups.QUARTZ_CRYSTAL).ticksRandomly().requiresTool()), false);
	public static final Block PRISMATIC_QUARTZ_CRYSTAL = registerBlock("prismatic_quartz_crystal",
		new PrismaticQuartzBlock(AbstractBlock.Settings.copy(Blocks.AMETHYST_BLOCK)
			.strength(1.5F).pistonBehavior(PistonBehavior.IGNORE).sounds(TacSoundGroups.QUARTZ_CRYSTAL).requiresTool().nonOpaque()
			.luminance(createLightLevelFromLitBlockState(15))), false);
	public static final Block ENGRAVED_QUARTZ_BLOCK = registerBlock("engraved_quartz_block",
		new Block(AbstractBlock.Settings.copy(Blocks.STONE)
			.strength(0.8F).sounds(BlockSoundGroup.STONE).requiresTool()), false);
	public static final Block QUARTZ_GLASS = registerBlock("quartz_glass",
		new QuartzGlassBlock(AbstractBlock.Settings.copy(Blocks.GLASS)
			.strength(0.3F).sounds(BlockSoundGroup.GLASS).nonOpaque().requiresTool()), false);
	public static final Block QUARTZ_GLASS_PANE = registerBlock("quartz_glass_pane",
		new QuartzGlassPaneBlock(AbstractBlock.Settings.copy(Blocks.GLASS)
			.strength(0.3F).sounds(BlockSoundGroup.GLASS).requiresTool()), false);
	public static final Block NETHERITE_FRAME = registerBlock("netherite_frame",
		new NetheriteFrameBlock(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)
			.strength(20F, 1200.0F).sounds(BlockSoundGroup.NETHERITE).requiresTool()), true);
	public static final Block NETHERITE_STRENGTHENED_QUARTZ_GLASS = registerBlock("netherite_strengthened_quartz_glass",
		new TransparentBlock(AbstractBlock.Settings.copy(Blocks.GLASS)
			.strength(0.3F, 1200.0F).sounds(BlockSoundGroup.GLASS).nonOpaque().requiresTool()), false);
	public static final Block NETHERITE_STRENGTHENED_QUARTZ_GLASS_PANE = registerBlock("netherite_strengthened_quartz_glass_pane",
		new PaneBlock(AbstractBlock.Settings.copy(Blocks.GLASS)
			.strength(0.3F, 1200.0F).sounds(BlockSoundGroup.GLASS).requiresTool()), false);
	public static final Block NETHERITE_STRENGTHENED_QUARTZ_BLOCK = registerBlock("netherite_strengthened_quartz_block",
		new Block(AbstractBlock.Settings.copy(Blocks.NETHERITE_BLOCK)
			.strength(1.2F, 1200.0F).sounds(BlockSoundGroup.NETHERITE).requiresTool()), true);
	public static final Block NETHERITE_STRENGTHENED_QUARTZ_SLAB = registerBlock("netherite_strengthened_quartz_slab",
		new SlabBlock(AbstractBlock.Settings.copy(Blocks.NETHERITE_BLOCK)
			.strength(1.2F, 1200.0F).sounds(BlockSoundGroup.NETHERITE).requiresTool()), true);
	public static final Block NETHERITE_STRENGTHENED_QUARTZ_STAIRS = registerBlock("netherite_strengthened_quartz_stairs",
		new StairsBlock(TacBlocks.NETHERITE_STRENGTHENED_QUARTZ_BLOCK.getDefaultState(),
			AbstractBlock.Settings.copy(Blocks.NETHERITE_BLOCK)
			.strength(1.2F, 1200.0F).sounds(BlockSoundGroup.NETHERITE).requiresTool()), true);
	public static final Block NETHERITE_STRENGTHENED_QUARTZ_BRICKS = registerBlock("netherite_strengthened_quartz_bricks",
		new Block(AbstractBlock.Settings.copy(Blocks.NETHERITE_BLOCK)
			.strength(1.2F, 1200.0F).sounds(BlockSoundGroup.NETHERITE).requiresTool()), true);
	public static final Block NETHERITE_STRENGTHENED_CHISELED_QUARTZ_BLOCK = registerBlock("netherite_strengthened_chiseled_quartz_block",
		new Block(AbstractBlock.Settings.copy(Blocks.NETHERITE_BLOCK)
			.strength(1.2F, 1200.0F).sounds(BlockSoundGroup.NETHERITE).requiresTool()), true);
	public static final Block NETHERITE_STRENGTHENED_QUARTZ_PILLAR = registerBlock("netherite_strengthened_quartz_pillar",
		new PillarBlock(AbstractBlock.Settings.copy(Blocks.NETHERITE_BLOCK)
			.strength(1.2F, 1200.0F).sounds(BlockSoundGroup.NETHERITE).requiresTool()), true);
	public static final Block NETHERITE_STRENGTHENED_ENGRAVED_QUARTZ_BLOCK = registerBlock("netherite_strengthened_engraved_quartz_block",
		new Block(AbstractBlock.Settings.copy(Blocks.NETHERITE_BLOCK)
			.strength(1.2F, 1200.0F).sounds(BlockSoundGroup.NETHERITE).requiresTool()), true);
	public static final Block TAC_PLUSHIE = registerPlushieBlock("tac_plushie",
		new TacPlushieBlock(AbstractBlock.Settings.create().mapColor(MapColor.WHITE).instrument(NoteBlockInstrument.GUITAR.GUITAR)
			.strength(0.8F).sounds(BlockSoundGroup.WOOL)));


	private static Block registerBlock(String name, Block block, boolean fireproof) {
		if (fireproof) {
			registerFireproofBlockItem(name, block);
		}
		else {
			registerBlockItem(name, block);
		}
		return Registry.register(Registries.BLOCK, TacCorpsTrinkets.id(name), block);
	}

	private static void registerBlockItem(String name, Block block) {
		Registry.register(Registries.ITEM, TacCorpsTrinkets.id(name),
			new BlockItem(block, new Item.Settings()));
	}

	private static void registerFireproofBlockItem(String name, Block block) {
		Registry.register(Registries.ITEM, TacCorpsTrinkets.id(name),
			new BlockItem(block, new Item.Settings().fireproof()));
	}

	private static Block registerPlushieBlock(String name, Block block) {
		registerPlushieBlockItem(name, block);
		return Registry.register(Registries.BLOCK, TacCorpsTrinkets.id(name), block);
	}

	private static void registerPlushieBlockItem(String name, Block block) {
		Registry.register(Registries.ITEM, TacCorpsTrinkets.id(name),
			new TacPlushieItem(block, new Item.Settings()));
	}

	private static Block registerBlockWithoutItem(String name, Block block) {
		return Registry.register(Registries.BLOCK, TacCorpsTrinkets.id(name), block);
	}

	public static void registerModBlocks () {

	}

	private static ToIntFunction<BlockState> createLightLevelFromLitBlockState(int litLevel) {
		return (state) -> {
			return (Boolean)state.get(Properties.LIT) ? litLevel : 0;
		};
	}
}
