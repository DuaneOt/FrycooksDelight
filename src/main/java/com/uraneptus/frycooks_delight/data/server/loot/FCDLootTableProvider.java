package com.uraneptus.frycooks_delight.data.server.loot;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.Collections;
import java.util.List;

public class FCDLootTableProvider extends LootTableProvider {

    public FCDLootTableProvider(PackOutput packOutput) {
        super(packOutput, Collections.emptySet(), List.of(
                new LootTableProvider.SubProviderEntry(FCDBlockLoot::new, LootContextParamSets.BLOCK)
        ));
    }

}
