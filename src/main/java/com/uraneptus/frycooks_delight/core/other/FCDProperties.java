package com.uraneptus.frycooks_delight.core.other;

import com.uraneptus.frycooks_delight.core.registry.FCDBlocks;
import com.uraneptus.frycooks_delight.core.registry.FCDFluids;
import com.uraneptus.frycooks_delight.core.registry.FCDItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;

@SuppressWarnings("unused")
public class FCDProperties {

    public static final BlockBehaviour.Properties CRATE = BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD);
    public static final BlockBehaviour.Properties CANOLA_PLANT = BlockBehaviour.Properties.copy(Blocks.WHEAT);
    public static final BlockBehaviour.Properties WILD_CANOLA = BlockBehaviour.Properties.copy(Blocks.TALL_GRASS);
    public static final BlockBehaviour.Properties CANOLA_OIL = BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).noCollission().strength(100).noLootTable().replaceable().liquid().pushReaction(PushReaction.DESTROY);

    public static final Item.Properties CANOLA_OIL_BUCKET = new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1);

    public static final ForgeFlowingFluid.Properties CANOLA_OIL_FLUID = new ForgeFlowingFluid.Properties(FCDFluids.CANOLA_OIL_FLUID_TYPE, FCDFluids.CANOLA_OIL_SOURCE, FCDFluids.CANOLA_OIL_FLOWING)
            .bucket(FCDItems.CANOLA_OIL_BUCKET).block(FCDBlocks.CANOLA_OIL).tickRate(15);

}
