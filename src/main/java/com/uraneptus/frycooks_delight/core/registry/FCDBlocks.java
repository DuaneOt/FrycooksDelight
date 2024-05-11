package com.uraneptus.frycooks_delight.core.registry;

import com.uraneptus.frycooks_delight.FrycooksDelight;
import com.uraneptus.frycooks_delight.common.blocks.CanolaOilFluidBlock;
import com.uraneptus.frycooks_delight.common.blocks.CanolaPlantBlock;
import com.uraneptus.frycooks_delight.core.other.FCDProperties;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.block.WildCropBlock;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = FrycooksDelight.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FCDBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, FrycooksDelight.MOD_ID);

    public static final RegistryObject<Block> CANOLA_CRATE = registerWithBlockItem("canola_crate", () -> new Block(FCDProperties.CRATE));
    public static final RegistryObject<Block> CANOLA_PLANT = registerWithoutItem("canola_plant", () -> new CanolaPlantBlock(FCDProperties.CANOLA_PLANT)); //TODO make compostable
    public static final RegistryObject<Block> WILD_CANOLA = registerWithBlockItem("wild_canola", () -> new WildCropBlock(MobEffects.MOVEMENT_SPEED, 6,  FCDProperties.WILD_CANOLA)); //TODO make compostable
    public static final RegistryObject<LiquidBlock> CANOLA_OIL = registerWithoutItem("canola_oil", () -> new CanolaOilFluidBlock(FCDFluids.CANOLA_OIL_SOURCE, FCDProperties.CANOLA_OIL));

    public static <T extends Block> RegistryObject<T> registerWithBlockItem(String name, Supplier<T> blockSupplier) {
        RegistryObject<T> blockObj = BLOCKS.register(name, blockSupplier);
        FCDItems.ITEMS.register(name, () -> new BlockItem(blockObj.get(), new Item.Properties()));
        return blockObj;
    }

    public static <T extends Block> RegistryObject<T> registerWithoutItem(String name, Supplier<T> blockSupplier) {
        return BLOCKS.register(name, blockSupplier);
    }

}
