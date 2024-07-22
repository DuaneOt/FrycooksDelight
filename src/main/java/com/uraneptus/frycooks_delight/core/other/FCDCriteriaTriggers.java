package com.uraneptus.frycooks_delight.core.other;

import com.uraneptus.frycooks_delight.FrycooksDelight;
import com.uraneptus.frycooks_delight.common.advancement.CodeTrigger;
import net.minecraft.advancements.CriteriaTriggers;

public class FCDCriteriaTriggers {
    public static CodeTrigger FRYING_ICE;
    public static CodeTrigger OILED_PIG;

    public static void init() {
        FRYING_ICE = CriteriaTriggers.register(new CodeTrigger(FrycooksDelight.modPrefix("frying_ice")));
        OILED_PIG = CriteriaTriggers.register(new CodeTrigger(FrycooksDelight.modPrefix("oiled_pig")));
    }
}
