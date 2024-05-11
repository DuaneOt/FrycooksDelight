package com.uraneptus.frycooks_delight.core.registry;

import com.uraneptus.frycooks_delight.FrycooksDelight;
import com.uraneptus.frycooks_delight.core.other.FCDProperties;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.registry.ModBlocks;

public class FCDItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FrycooksDelight.MOD_ID);

    public static final RegistryObject<Item> CANOLA = ITEMS.register("canola", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CANOLA_SEEDS = ITEMS.register("canola_seeds", () ->  new ItemNameBlockItem(FCDBlocks.CANOLA_PLANT.get(), new Item.Properties()));
    public static final RegistryObject<Item> CANOLA_OIL = ITEMS.register("canola_oil", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CANOLA_OIL_BUCKET = ITEMS.register("canola_oil_bucket", () -> new BucketItem(FCDFluids.CANOLA_OIL_SOURCE, FCDProperties.CANOLA_OIL_BUCKET));

    public static final RegistryObject<Item> FRIED_POTATO = ITEMS.register("fried_potato", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PLAIN_DONUT = ITEMS.register("plain_donut", () -> new Item(new Item.Properties()));

}
