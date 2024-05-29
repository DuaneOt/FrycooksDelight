package com.uraneptus.frycooks_delight.core.registry;

import com.uraneptus.frycooks_delight.FrycooksDelight;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

import java.util.HashMap;
import java.util.Map;

public class FCDDamageTypes {
    public static Map<ResourceKey<DamageType>, DamageType> damageTypeMap = new HashMap<>();
    public static final ResourceKey<DamageType> FRYING = register(new DamageType("frying", 0));

    private static ResourceKey<DamageType> register(DamageType damageType) {
        ResourceKey<DamageType> key = ResourceKey.create(Registries.DAMAGE_TYPE, FrycooksDelight.modPrefix(damageType.msgId()));
        damageTypeMap.put(key, damageType);
        return key;
    }
}