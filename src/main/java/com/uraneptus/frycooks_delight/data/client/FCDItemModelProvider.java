package com.uraneptus.frycooks_delight.data.client;

import com.uraneptus.frycooks_delight.FrycooksDelight;
import com.uraneptus.frycooks_delight.core.registry.FCDItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

import static com.uraneptus.frycooks_delight.data.FCDDatagenUtil.*;

@SuppressWarnings({"unused", "SameParameterValue"})
public class FCDItemModelProvider extends ItemModelProvider {

    public FCDItemModelProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, FrycooksDelight.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(FCDItems.CANOLA);
        basicItem(FCDItems.CANOLA_SEEDS);
        basicItem(FCDItems.CANOLA_OIL);
        basicItem(FCDItems.FRIED_POTATO);
        basicItem(FCDItems.PLAIN_DONUT);
        basicItem(FCDItems.HOT_GREASE_BUCKET);
        basicItem(FCDItems.BURNT_MORSEL);
        basicItem(FCDItems.LARD);
    }

    private void basicBlockItem(Supplier<? extends Block> blockForItem) {
        withExistingParent(name(blockForItem.get()), modBlockLocation(name(blockForItem.get())));
    }

    private void basicItem(Supplier<? extends Item> item) {
        basicItem(item.get());
    }

    private void basicItemHandheld(Supplier<? extends Item> item) {
        withExistingParent(name(item.get()), HANDHELD)
                .texture(LAYER0, modItemLocation(name(item.get())));
    }

    private void blockItemWithItemTexture(Supplier<? extends Block> blockForItem) {
        basicItem(blockForItem.get().asItem());
    }

    private void itemFromBlockTexture(Supplier<? extends Block> block) {
        withExistingParent(name(block.get()), GENERATED).texture(LAYER0, modBlockLocation(name(block.get())));
    }

    private void modButtonBlockItem(Supplier<? extends Block> blockForItem, Supplier<? extends Block> blockForTexture) {
        buttonInventory(name(blockForItem.get()), modBlockLocation(name(blockForTexture.get())));
    }

    private void copperButtonBlockItem(Supplier<? extends Block> blockForItem, Supplier<? extends Block> blockForTexture) {
        buttonInventory(name(blockForItem.get()), vanillaBlockLocation(name(blockForTexture.get())));
    }

    private void fenceBlockItem(Supplier<? extends Block> block, Supplier<? extends Block> blockForTexture) {
        fenceInventory(name(block.get()), modBlockLocation(name(blockForTexture.get())));
    }
    private void wallBlockItem(Supplier<? extends Block> block, Supplier<? extends Block> blockForTexture) {
        wallInventory(name(block.get()), modBlockLocation(name(blockForTexture.get())));
    }

    private void basicBlockItemWithSuffix(Supplier<? extends Block> block, String suffix) {
        withExistingParent(name(block.get()), modBlockLocation(name(block.get()) + suffix));
    }
}
