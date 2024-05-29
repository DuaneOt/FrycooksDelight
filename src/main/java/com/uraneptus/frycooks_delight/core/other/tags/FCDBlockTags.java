package com.uraneptus.frycooks_delight.core.other.tags;

import com.uraneptus.frycooks_delight.FrycooksDelight;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class FCDBlockTags {
    public static final TagKey<Block> FRY_HEAT_SOURCES = TagKey.create(Registries.BLOCK, FrycooksDelight.modPrefix("fry_heat_sources"));

}
