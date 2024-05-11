package com.uraneptus.frycooks_delight.data.server.tags;

import com.uraneptus.frycooks_delight.FrycooksDelight;
import com.uraneptus.frycooks_delight.core.other.tags.FCDItemTags;
import com.uraneptus.frycooks_delight.core.registry.FCDBlocks;
import com.uraneptus.frycooks_delight.core.registry.FCDItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unchecked")
public class FCDItemTagsProvider extends ItemTagsProvider {

    public FCDItemTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> pProvider, CompletableFuture<TagsProvider.TagLookup<Block>> blockProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, pProvider, blockProvider, FrycooksDelight.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(FCDItemTags.CANOLA_SEEDS).add(FCDItems.CANOLA_SEEDS.get());
        tag(Tags.Items.SEEDS).addTag(FCDItemTags.CANOLA_SEEDS);
        tag(FCDItemTags.CANOLA_CROPS).add(FCDItems.CANOLA.get());
        tag(Tags.Items.CROPS).addTag(FCDItemTags.CANOLA_CROPS);

        tag(ItemTags.SMALL_FLOWERS).add(FCDBlocks.WILD_CANOLA.get().asItem());
    }
}
