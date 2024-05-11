package com.uraneptus.frycooks_delight.data;

import com.uraneptus.frycooks_delight.FrycooksDelight;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.registries.ForgeRegistries;
import vectorwing.farmersdelight.FarmersDelight;

import java.util.List;

@SuppressWarnings("unused")
public class FCDDatagenUtil {
    public static final String LAYER0 = "layer0";
    public static final String PLANT = "plant";
    public static final String ENTITY = "builtin/entity";
    public static final ResourceLocation POTTED_CROSS  = vanillaBlockLocation("flower_pot_cross");
    public static final ResourceLocation GENERATED = vanillaItemLocation("generated");
    public static final ResourceLocation HANDHELD = vanillaItemLocation("handheld");
    public static final ResourceLocation SPAWN_EGG = vanillaItemLocation("template_spawn_egg");

    @SafeVarargs
    public static HolderSet<PlacedFeature> getPlacedHolderSet(BootstapContext<?> context, ResourceKey<PlacedFeature>... placedFeatures) {
        List<Holder<PlacedFeature>> holders = new ObjectArrayList<>();
        for (ResourceKey<PlacedFeature> feature : placedFeatures) {
            holders.add(context.lookup(Registries.PLACED_FEATURE).getOrThrow(feature));
        }
        return HolderSet.direct(holders);
    }

    public static String name(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block).getPath();
    }

    public static String name(Item item) {
        return ForgeRegistries.ITEMS.getKey(item).getPath();
    }

    public static ResourceLocation modBlockLocation(String path) {
        return FrycooksDelight.modPrefix(ModelProvider.BLOCK_FOLDER + "/" + path);
    }

    public static ResourceLocation modItemLocation(String path) {
        return FrycooksDelight.modPrefix(ModelProvider.ITEM_FOLDER + "/" + path);
    }

    public static ResourceLocation vanillaBlockLocation(String path) {
        return new ResourceLocation(ModelProvider.BLOCK_FOLDER + "/" + path);
    }

    public static ResourceLocation vanillaItemLocation(String path) {
        return new ResourceLocation(ModelProvider.ITEM_FOLDER + "/" + path);
    }

    public static ResourceLocation fdBlockLocation(String path) {
        return new ResourceLocation(FarmersDelight.MODID, ModelProvider.BLOCK_FOLDER + "/" + path);
    }

    public static ResourceLocation craftingPath(String name) {
        return FrycooksDelight.modPrefix("crafting/" + name);
    }

    public static ResourceLocation smeltingPath(String name) {
        return FrycooksDelight.modPrefix("smelting/" + name);
    }

    public static ResourceLocation blastingPath(String name) {
        return FrycooksDelight.modPrefix("blasting/" + name);
    }

    public static ResourceLocation smokingPath(String name) {
        return FrycooksDelight.modPrefix("smoking/" + name);
    }

    public static ResourceLocation campfire_cookingPath(String name) {
        return FrycooksDelight.modPrefix("campfire_cooking/" + name);
    }

    public static ResourceLocation stonecuttingPath(String name) {
        return FrycooksDelight.modPrefix("stonecutting/" + name);
    }

    public static ResourceLocation smithingPath(String name) {
        return FrycooksDelight.modPrefix("smithing/" + name);
    }
}