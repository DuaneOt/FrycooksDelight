package com.uraneptus.frycooks_delight;

import com.uraneptus.frycooks_delight.core.registry.*;
import com.uraneptus.frycooks_delight.data.client.FCDBlockStateProvider;
import com.uraneptus.frycooks_delight.data.client.FCDItemModelProvider;
import com.uraneptus.frycooks_delight.data.client.FCDLangProvider;
import com.uraneptus.frycooks_delight.data.client.FCDSoundDefinitionsProvider;
import com.uraneptus.frycooks_delight.data.server.FCDDatapackBuiltinEntriesProvider;
import com.uraneptus.frycooks_delight.data.server.recipe.FCDRecipeProvider;
import com.uraneptus.frycooks_delight.data.server.advancements.FCDAdvancementProvider;
import com.uraneptus.frycooks_delight.data.server.loot.FCDLootTableProvider;
import com.uraneptus.frycooks_delight.data.server.tags.FCDBiomeTagsProvider;
import com.uraneptus.frycooks_delight.data.server.tags.FCDBlockTagsProvider;
import com.uraneptus.frycooks_delight.data.server.tags.FCDItemTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.concurrent.CompletableFuture;

@Mod(FrycooksDelight.MOD_ID)
@Mod.EventBusSubscriber(modid = FrycooksDelight.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FrycooksDelight {
    public static final String MOD_ID = "frycooks_delight";

    public FrycooksDelight() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::setup);
        bus.addListener(this::gatherData);

        FCDBlocks.BLOCKS.register(bus);
        FCDItems.ITEMS.register(bus);
        FCDFluids.FLUID_TYPES.register(bus);
        FCDFluids.FLUIDS.register(bus);
        FCDCreativeTabs.TABS.register(bus);
        FCDRecipes.RECIPE_TYPES.register(bus);
        FCDRecipes.SERIALIZERS.register(bus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    public static ResourceLocation modPrefix(String path) {
        return new ResourceLocation(FrycooksDelight.MOD_ID, path);
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {

        });
    }

    @SubscribeEvent
    public void gatherData(GatherDataEvent event) {
        boolean includeClient = event.includeClient();
        boolean includeServer = event.includeServer();
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(includeClient, new FCDBlockStateProvider(packOutput, fileHelper));
        generator.addProvider(includeClient, new FCDItemModelProvider(packOutput, fileHelper));
        generator.addProvider(includeClient, new FCDSoundDefinitionsProvider(packOutput, fileHelper));
        generator.addProvider(includeClient, new FCDLangProvider(packOutput));

        FCDBlockTagsProvider blockTagProvider = new FCDBlockTagsProvider(packOutput, lookupProvider, fileHelper);
        generator.addProvider(includeServer, blockTagProvider);
        generator.addProvider(includeServer, new FCDItemTagsProvider(packOutput, lookupProvider, blockTagProvider.contentsGetter(), fileHelper));
        generator.addProvider(includeServer, new FCDBiomeTagsProvider(packOutput, lookupProvider, fileHelper));
        generator.addProvider(includeServer, new FCDLootTableProvider(packOutput));
        generator.addProvider(includeServer, new FCDAdvancementProvider(packOutput, lookupProvider, fileHelper));
        generator.addProvider(includeServer, new FCDRecipeProvider(packOutput));
        generator.addProvider(includeServer, new FCDDatapackBuiltinEntriesProvider(packOutput, lookupProvider));
    }
}