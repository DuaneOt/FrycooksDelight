package com.uraneptus.frycooks_delight.core.other.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class FCDItemTags {

    public static final TagKey<Item> CANOLA_SEEDS = TagKey.create(Registries.ITEM, new ResourceLocation("forge", "seeds/canola"));
    public static final TagKey<Item> CANOLA_CROPS = TagKey.create(Registries.ITEM, new ResourceLocation("forge", "crops/canola"));

}
