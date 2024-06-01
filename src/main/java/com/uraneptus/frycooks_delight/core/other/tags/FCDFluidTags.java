package com.uraneptus.frycooks_delight.core.other.tags;

import com.uraneptus.frycooks_delight.FrycooksDelight;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;

public class FCDFluidTags {
    public static final TagKey<Fluid> HOT_GREASE_FLUID = TagKey.create(Registries.FLUID, FrycooksDelight.modPrefix("hot_grease"));

}
