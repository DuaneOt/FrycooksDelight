package com.uraneptus.frycooks_delight.core.registry;

import com.uraneptus.frycooks_delight.FrycooksDelight;
import com.uraneptus.frycooks_delight.common.CanolaOilFluidType;
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

    public static final RegistryObject<FluidType> CANOLA_OIL_FLUID_TYPE = FLUID_TYPES.register("canola_oil", CanolaOilFluidType::new);

    public static final RegistryObject<FlowingFluid> CANOLA_OIL_SOURCE = FLUIDS.register("canola_oil", () -> new ForgeFlowingFluid.Source(FCDProperties.CANOLA_OIL_FLUID));
    public static final RegistryObject<FlowingFluid> CANOLA_OIL_FLOWING = FLUIDS.register("canola_oil_flowing", () -> new ForgeFlowingFluid.Flowing(FCDProperties.CANOLA_OIL_FLUID));
}
