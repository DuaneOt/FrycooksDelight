package com.uraneptus.frycooks_delight.core.registry;

import com.uraneptus.frycooks_delight.FrycooksDelight;
import com.uraneptus.frycooks_delight.core.other.FCDProperties;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class FCDItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, FrycooksDelight.MOD_ID);
    public static final List<RegistryObject<Item>> FRIED_FOODS = new ArrayList<>();

    public static final RegistryObject<Item> CANOLA = ITEMS.register("canola", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CANOLA_SEEDS = ITEMS.register("canola_seeds", () ->  new ItemNameBlockItem(FCDBlocks.CANOLA_PLANT.get(), new Item.Properties()));
    public static final RegistryObject<Item> CANOLA_OIL = ITEMS.register("canola_oil", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> HOT_GREASE_BUCKET = ITEMS.register("hot_grease_bucket", () -> new BucketItem(FCDFluids.HOT_GREASE_SOURCE, FCDProperties.HOT_GREASE_BUCKET));

    public static final RegistryObject<Item> FRIED_POTATO = registerFriedFood("fried_potato", FCDProperties.FRIED_POTATO);
    public static final RegistryObject<Item> PLAIN_DONUT = registerFriedFood("plain_donut", FCDProperties.PLAIN_DONUT);
    public static final RegistryObject<Item> FRIED_ONION_RING = registerFriedFood("fried_onion_ring", FCDProperties.FRIED_ONION_RING);
    public static final RegistryObject<Item> FRIED_CHICKEN_LEG = registerFriedFood("fried_chicken_leg", FCDProperties.FRIED_CHICKEN_LEG);
    public static final RegistryObject<Item> FRIED_FISH_SLICE = registerFriedFood("fried_fish_slice", FCDProperties.FRIED_FISH_SLICE);
    public static final RegistryObject<Item> BURNT_MORSEL = ITEMS.register("burnt_morsel", () -> new Item(FCDProperties.BURNT_MORSEL));
    public static final RegistryObject<Item> LARD = ITEMS.register("lard", () -> new Item(new Item.Properties()));

    public static RegistryObject<Item> registerFriedFood(String name, Item.Properties properties) {
        RegistryObject<Item> obj = ITEMS.register(name, () -> new Item(properties));
        FRIED_FOODS.add(obj);
        return obj;
    }
}
