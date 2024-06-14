package com.uraneptus.frycooks_delight.common.fluid;

import com.uraneptus.frycooks_delight.client.OilBubbleOptions;
import com.uraneptus.frycooks_delight.core.other.tags.FCDFluidTags;
import com.uraneptus.frycooks_delight.core.registry.FCDFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.ForgeFlowingFluid;

public abstract class HotGreaseFluid extends ForgeFlowingFluid {

    protected HotGreaseFluid(Properties properties) {
        super(properties);
    }

    @Override
    protected void animateTick(Level pLevel, BlockPos pPos, FluidState pState, RandomSource pRandom) {

        for (int i = 0; i < 2; i++) {
            double xPos = pPos.getX() + 0.125 + 0.75 * pRandom.nextFloat();
            double yPos = pPos.getY() + this.getHeight(pState, pLevel, pPos);
            double zPos = pPos.getZ() + 0.125 + 0.75 * pRandom.nextFloat();
            pLevel.addParticle(new OilBubbleOptions(Vec3.fromRGB24(5848363).toVector3f()), xPos, yPos, zPos, 0.0F, 0.02, 0.0F);
        }
    }

    @Override
    public boolean isSame(Fluid fluid) {
        return fluid.is(FCDFluidTags.HOT_GREASE_FLUID);
    }

    @Override
    public boolean move(FluidState state, LivingEntity entity, Vec3 movementVector, double gravity) {
        return super.move(state, entity, movementVector, gravity);
    }

    public static class Flowing extends HotGreaseFluid {
        public Flowing(Properties properties) {
            super(properties);
        }

        @Override
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getAmount(FluidState state) {
            return state.getValue(LEVEL);
        }

        @Override
        public boolean isSource(FluidState state) {
            return false;
        }
    }

    public static class Source extends HotGreaseFluid {
        public Source(Properties properties) {
            super(properties);
        }

        @Override
        public int getAmount(FluidState state) {
            return 8;
        }

        @Override
        public boolean isSource(FluidState state) {
            return true;
        }
    }
}
