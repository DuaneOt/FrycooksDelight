package com.uraneptus.frycooks_delight.data.client;

import com.uraneptus.frycooks_delight.FrycooksDelight;
import com.uraneptus.frycooks_delight.core.other.FCDTextUtil;
import com.uraneptus.frycooks_delight.core.registry.FCDBlocks;
import com.uraneptus.frycooks_delight.core.registry.FCDItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.function.Supplier;

public class FCDLangProvider extends LanguageProvider {

    public FCDLangProvider(PackOutput packOutput) {
        super(packOutput, FrycooksDelight.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        FCDItems.ITEMS.getEntries().forEach(this::forItem);
        FCDTextUtil.TRANSLATABLES.forEach(this::add);
        forBlock(FCDBlocks.CANOLA_PLANT);
    }

    protected void forItem(Supplier<? extends Item> item) {
        addItem(item, FCDTextUtil.createTranslation(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item.get())).getPath()));
    }

    protected void forBlock(Supplier<? extends Block> block) {
        addBlock(block, FCDTextUtil.createTranslation(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block.get())).getPath()));
    }
}