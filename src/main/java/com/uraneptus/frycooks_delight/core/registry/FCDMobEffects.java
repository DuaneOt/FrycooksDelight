package com.uraneptus.frycooks_delight.core.registry;

import com.uraneptus.frycooks_delight.FrycooksDelight;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FCDMobEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, FrycooksDelight.MOD_ID);

    public static final RegistryObject<MobEffect> OILED = EFFECTS.register("oiled", () -> (new MobEffect(MobEffectCategory.BENEFICIAL, 16242474)).addAttributeModifier(Attributes.MOVEMENT_SPEED, "4d00e4ec-176c-4984-a2d1-17c241981d8a", 0.5F, AttributeModifier.Operation.MULTIPLY_TOTAL));
}
