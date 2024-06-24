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
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.tag.ModTags;

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

        tag(FCDItemTags.IS_FRIED).add(
                FCDItems.FRIED_POTATO.get(),
                FCDItems.FRIED_ONION_RING.get(),
                FCDItems.FRIED_CHICKEN_LEG.get(),
                FCDItems.FRIED_FISH_SLICE.get(),
                FCDItems.PLAIN_DONUT.get()
        );

        tag(FCDItemTags.HAS_FISH_SLICE).add(
                Items.COD,
                Items.SALMON
        );

        tag(FCDItemTags.FISH_SLICES).add(
                ModItems.COD_SLICE.get(),
                ModItems.SALMON_SLICE.get()
        );

        tag(FCDItemTags.CAUSE_OIL_OVERFLOW).add(
                Items.ICE,
                Items.BLUE_ICE,
                Items.PACKED_ICE,
                Items.SNOW,
                Items.SNOW_BLOCK,
                Items.SNOWBALL
        );

        tag(FCDItemTags.BURNS_TO_MORSEL).add(
                Items.MUSHROOM_STEM,
                Items.BROWN_MUSHROOM,
                Items.RED_MUSHROOM,
                Items.BROWN_MUSHROOM_BLOCK,
                Items.RED_MUSHROOM_BLOCK,
                Items.CRIMSON_FUNGUS,
                Items.WARPED_FUNGUS,
                Items.SUGAR_CANE,
                Items.CACTUS,
                Items.SHROOMLIGHT,
                Items.CHORUS_PLANT,
                Items.CHORUS_FRUIT,
                Items.CHORUS_FLOWER,
                Items.TURTLE_EGG,
                Items.SNIFFER_EGG,
                Items.PITCHER_POD,
                Items.NETHER_WART,
                Items.SEA_PICKLE,
                Items.KELP,
                Items.DRIED_KELP_BLOCK,
                Items.SPONGE,
                Items.WET_SPONGE,
                Items.MELON,
                Items.PUMPKIN,
                Items.CARVED_PUMPKIN,
                Items.JACK_O_LANTERN,
                Items.HAY_BLOCK,
                Items.HONEYCOMB_BLOCK,
                Items.SLIME_BLOCK,
                Items.HONEY_BLOCK,
                Items.OCHRE_FROGLIGHT,
                Items.PEARLESCENT_FROGLIGHT,
                Items.VERDANT_FROGLIGHT,
                Items.SCULK,
                Items.SCULK_SENSOR,
                Items.ZOMBIE_HEAD,
                Items.CREEPER_HEAD,
                Items.PIGLIN_HEAD,
                Items.DRAGON_HEAD,
                Items.ENDER_EYE,
                Items.BONE_MEAL,
                Items.ENDER_PEARL,
                Items.LEATHER_BOOTS,
                Items.LEATHER_CHESTPLATE,
                Items.LEATHER_HELMET,
                Items.LEATHER_LEGGINGS,
                Items.LEATHER_HORSE_ARMOR,
                Items.CAKE,
                Items.WHEAT,
                Items.LEATHER,
                Items.RABBIT_HIDE,
                Items.HONEYCOMB,
                Items.INK_SAC,
                Items.GLOW_INK_SAC,
                Items.SCUTE,
                Items.SLIME_BALL,
                Items.CLAY_BALL,
                Items.FERMENTED_SPIDER_EYE,
                Items.RABBIT_FOOT,
                Items.GLISTERING_MELON_SLICE,
                Items.MAGMA_CREAM,
                Items.PHANTOM_MEMBRANE,
                ModItems.BROWN_MUSHROOM_COLONY.get(),
                ModItems.RED_MUSHROOM_COLONY.get(),
                ModItems.RICE_BALE.get(),
                ModItems.STRAW_BALE.get(),
                ModItems.STRAW.get(),
                ModItems.TREE_BARK.get(),
                ModItems.CANVAS.get()
        ).addTags(Tags.Items.EGGS, Tags.Items.DYES, ModTags.WILD_CROPS_ITEM, ItemTags.WART_BLOCKS);

        tag(FCDItemTags.OIL_DESTROYS).add(
            Items.MOSS_BLOCK,
            Items.MOSS_CARPET,
            Items.GRASS,
            Items.TALL_GRASS,
            Items.FERN,
            Items.LARGE_FERN,
            Items.DEAD_BUSH,
            Items.SPORE_BLOSSOM,
            Items.BAMBOO,
            Items.CRIMSON_ROOTS,
            Items.WARPED_ROOTS,
            Items.NETHER_SPROUTS,
            Items.WEEPING_VINES,
            Items.TWISTING_VINES,
            Items.VINE,
            Items.BIG_DRIPLEAF,
            Items.SMALL_DRIPLEAF,
            Items.GLOW_LICHEN,
            Items.HANGING_ROOTS,
            Items.FROGSPAWN,
            Items.LILY_PAD,
            Items.SEAGRASS,
            Items.SCULK_VEIN,
            Items.COBWEB,
            Items.STRING,
            Items.NAME_TAG,
            Items.MAP,
            Items.FILLED_MAP,
            Items.FEATHER,
            Items.PAPER,
            Items.SUGAR,
            ModItems.SAFETY_NET.get(),
            ModItems.CANVAS_RUG.get(),
            ModItems.ROPE.get(),
            ModItems.SANDY_SHRUB.get()
        ).addTags(ItemTags.LEAVES,
                ItemTags.SAPLINGS,
                ItemTags.FLOWERS,
                ItemTags.TALL_FLOWERS,
                ItemTags.CANDLES,
                ItemTags.BOOKSHELF_BOOKS,
                Tags.Items.SEEDS
        );
    }
}
