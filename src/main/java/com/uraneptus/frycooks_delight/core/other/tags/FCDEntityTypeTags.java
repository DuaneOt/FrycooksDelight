package com.uraneptus.frycooks_delight.core.other.tags;

import com.uraneptus.frycooks_delight.FrycooksDelight;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

public class FCDEntityTypeTags {

    public static final TagKey<EntityType<?>> FRYING_IMMUNE_ENTITY_TYPES = TagKey.create(Registries.ENTITY_TYPE, FrycooksDelight.modPrefix("frying_immune_entity_types"));
    public static final TagKey<EntityType<?>> CAN_BE_OILED = TagKey.create(Registries.ENTITY_TYPE, FrycooksDelight.modPrefix("can_be_oiled"));
    public static final TagKey<EntityType<?>> PANIC_WHEN_OILED = TagKey.create(Registries.ENTITY_TYPE, FrycooksDelight.modPrefix("panic_when_oiled"));

}
