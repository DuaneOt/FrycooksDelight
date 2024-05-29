package com.uraneptus.frycooks_delight.core.registry;

import com.uraneptus.frycooks_delight.FrycooksDelight;
import com.uraneptus.frycooks_delight.common.fluid.HotGreaseFluidType;
import com.uraneptus.frycooks_delight.core.other.FCDProperties;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FCDFluids {
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, FrycooksDelight.MOD_ID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, FrycooksDelight.MOD_ID);

    public static final RegistryObject<FluidType> HOT_GREASE_FLUID_TYPE = FLUID_TYPES.register("hot_grease", HotGreaseFluidType::new);

    public static final RegistryObject<FlowingFluid> HOT_GREASE_SOURCE = FLUIDS.register("hot_grease", () -> new ForgeFlowingFluid.Source(FCDProperties.HOT_GREASE_FLUID));
    public static final RegistryObject<FlowingFluid> HOT_GREASE_FLOWING = FLUIDS.register("hot_grease_flowing", () -> new ForgeFlowingFluid.Flowing(FCDProperties.HOT_GREASE_FLUID));
}
