package com.uraneptus.frycooks_delight.core.registry;

import com.mojang.serialization.Codec;
import com.uraneptus.frycooks_delight.FrycooksDelight;
import com.uraneptus.frycooks_delight.client.OilBubbleOptions;
import com.uraneptus.frycooks_delight.client.OilBubbleParticle;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FCDParticleTypes {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, FrycooksDelight.MOD_ID);

    public static final RegistryObject<ParticleType<OilBubbleOptions>> OIL_BUBBLE = PARTICLES.register("oil_bubble", () -> new ParticleType<>(false, OilBubbleOptions.DESERIALIZER) {
        @Override
        public Codec<OilBubbleOptions> codec() {
            return OilBubbleOptions.CODEC;
        }
    });
}
