package com.uraneptus.frycooks_delight.data.server.tags;

import com.uraneptus.frycooks_delight.FrycooksDelight;
import com.uraneptus.frycooks_delight.core.other.tags.FCDEntityTypeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class FCDEntityTypeTagsProvider extends EntityTypeTagsProvider {

    public FCDEntityTypeTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pProvider, FrycooksDelight.MOD_ID, existingFileHelper);
    }

    protected void addTags(HolderLookup.Provider pProvider) {
        tag(FCDEntityTypeTags.FRYING_IMMUNE_ENTITY_TYPES).add(EntityType.PIGLIN, EntityType.PIGLIN_BRUTE, EntityType.ZOMBIFIED_PIGLIN, EntityType.HOGLIN, EntityType.ZOGLIN);
        tag(FCDEntityTypeTags.CAN_BE_OILED).add(EntityType.PIGLIN, EntityType.PIGLIN_BRUTE, EntityType.ZOMBIFIED_PIGLIN, EntityType.HOGLIN, EntityType.ZOGLIN, EntityType.PIG);
        tag(FCDEntityTypeTags.PANIC_WHEN_OILED).add(EntityType.HOGLIN, EntityType.ZOGLIN, EntityType.PIG);
    }
}
