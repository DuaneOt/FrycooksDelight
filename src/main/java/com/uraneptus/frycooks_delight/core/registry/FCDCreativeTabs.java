package com.uraneptus.frycooks_delight.core.registry;

import com.uraneptus.frycooks_delight.FrycooksDelight;
import com.uraneptus.frycooks_delight.core.other.FCDTextUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FCDCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, FrycooksDelight.MOD_ID);

    public static final RegistryObject<CreativeModeTab> ARTIFACT_TAB = TABS.register("frycooks_delight_tab", () -> CreativeModeTab.builder()
            .title(FCDTextUtil.addSMTranslatable("itemGroup.frycooks_delight", "Frycook's Delight"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> FCDItems.CANOLA.get().getDefaultInstance())
            .displayItems((parameters, output) -> FCDItems.ITEMS.getEntries().forEach(regObj -> output.accept(regObj.get())))
            .build());

}
