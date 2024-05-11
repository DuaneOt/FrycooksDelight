package com.uraneptus.frycooks_delight.data.server.loot;

import com.uraneptus.frycooks_delight.core.registry.FCDBlocks;
import com.uraneptus.frycooks_delight.core.registry.FCDItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class FCDBlockLoot extends BlockLootSubProvider {
    private static final Set<Item> EXPLOSION_RESISTANT = Set.of();

    protected FCDBlockLoot() {
        super(EXPLOSION_RESISTANT, FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return FCDBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

    @Override
    protected void generate() {
        this.dropSelf(FCDBlocks.CANOLA_CRATE.get());
        this.dropSelf(FCDBlocks.CANOLA_PLANT.get());

        LootItemCondition.Builder lootitemcondition$builder1 = LootItemBlockStatePropertyCondition.hasBlockStateProperties(FCDBlocks.CANOLA_PLANT.get()).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CropBlock.AGE, 7));
        this.add(FCDBlocks.CANOLA_PLANT.get(), this.createCropDrops(FCDBlocks.CANOLA_PLANT.get(), FCDItems.CANOLA.get(), FCDItems.CANOLA_SEEDS.get(), lootitemcondition$builder1));
        this.add(FCDBlocks.WILD_CANOLA.get(), createWildCropDrops(FCDBlocks.WILD_CANOLA.get(), FCDItems.CANOLA.get(), FCDItems.CANOLA_SEEDS.get()));
    }

    @Override
    protected LootTable.Builder createCropDrops(Block pCropBlock, Item pGrownCropItem, Item pSeedsItem, LootItemCondition.Builder pDropGrownCropCondition) {
        return this.applyExplosionDecay(pCropBlock, LootTable.lootTable().withPool(LootPool.lootPool().add(LootItem.lootTableItem(pGrownCropItem).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))).when(pDropGrownCropCondition).otherwise(LootItem.lootTableItem(pSeedsItem)))).withPool(LootPool.lootPool().add(LootItem.lootTableItem(pSeedsItem)).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))).when(pDropGrownCropCondition)));

    }

    protected LootTable.Builder createWildCropDrops(Block pCropBlock, Item pGrownCropItem, Item pSeedsItem) {
        return this.applyExplosionDecay(pCropBlock, LootTable.lootTable().withPool(LootPool.lootPool().add(AlternativesEntry.alternatives(LootItem.lootTableItem(pCropBlock).when(HAS_SHEARS), LootItem.lootTableItem(pSeedsItem).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))))).withPool(LootPool.lootPool().add(LootItem.lootTableItem(pGrownCropItem)).when(HAS_SHEARS.invert())));

    }
}
