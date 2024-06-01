package com.uraneptus.frycooks_delight.common.blocks;

import com.uraneptus.frycooks_delight.core.registry.FCDDamageTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;

import java.util.function.Supplier;

public class HotGreaseFluidBlock extends LiquidBlock {

    public HotGreaseFluidBlock(Supplier<? extends FlowingFluid> pFluid, Properties pProperties) {
        super(pFluid, pProperties);
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (pEntity instanceof LivingEntity) {
            pEntity.hurt(pLevel.damageSources().source(FCDDamageTypes.FRYING), 1.0F);
        }
    }
}
