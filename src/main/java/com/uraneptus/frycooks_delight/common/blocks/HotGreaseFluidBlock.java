package com.uraneptus.frycooks_delight.common.blocks;

import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.FlowingFluid;

import java.util.function.Supplier;

public class HotGreaseFluidBlock extends LiquidBlock {

    public HotGreaseFluidBlock(Supplier<? extends FlowingFluid> pFluid, Properties pProperties) {
        super(pFluid, pProperties);
    }
}
