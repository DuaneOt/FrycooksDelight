package com.uraneptus.frycooks_delight.data.server.tags;

import com.uraneptus.frycooks_delight.FrycooksDelight;
import com.uraneptus.frycooks_delight.core.registry.FCDBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class FCDBlockTagsProvider extends BlockTagsProvider {

    public FCDBlockTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> pProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, pProvider, FrycooksDelight.MOD_ID, existingFileHelper);
    }

    @Override
    public void addTags(HolderLookup.Provider pProvider) {
        tag(BlockTags.CROPS).add(FCDBlocks.CANOLA_PLANT.get());
        tag(BlockTags.SMALL_FLOWERS).add(FCDBlocks.WILD_CANOLA.get());
    }
}
