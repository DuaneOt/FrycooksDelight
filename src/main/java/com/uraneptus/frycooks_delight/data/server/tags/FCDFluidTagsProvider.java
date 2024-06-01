package com.uraneptus.frycooks_delight.data.server.tags;

import com.uraneptus.frycooks_delight.FrycooksDelight;
import com.uraneptus.frycooks_delight.core.other.tags.FCDFluidTags;
import com.uraneptus.frycooks_delight.core.registry.FCDFluids;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class FCDFluidTagsProvider extends FluidTagsProvider {

    public FCDFluidTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, completableFuture, FrycooksDelight.MOD_ID, existingFileHelper);
    }

    @Override
    public void addTags(HolderLookup.Provider lookupProvider) {
        tag(FCDFluidTags.HOT_GREASE_FLUID).add(FCDFluids.HOT_GREASE_FLOWING.get(), FCDFluids.HOT_GREASE_SOURCE.get());
    }
}
