package com.uraneptus.frycooks_delight.core.other.tags;

import com.uraneptus.frycooks_delight.FrycooksDelight;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class FCDBiomeTags {
    public static final TagKey<Biome> CANOLA_SPAWN_IN = TagKey.create(Registries.BIOME, FrycooksDelight.modPrefix("canola_spawn_in"));

}
