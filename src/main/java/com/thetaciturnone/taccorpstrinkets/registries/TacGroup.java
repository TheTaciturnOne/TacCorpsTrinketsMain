package com.thetaciturnone.taccorpstrinkets.registries;

import com.thetaciturnone.taccorpstrinkets.TacCorpsTrinkets;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class TacGroup {
    public static final ItemGroup TACCORP_TRINKETS = FabricItemGroupBuilder.build(new Identifier(TacCorpsTrinkets.MOD_ID, "taccorp_trinkets"),
            () -> new ItemStack(TacBlocks.NETHERITE_STRENGTHENED_QUARTZ_BLOCK));
}
