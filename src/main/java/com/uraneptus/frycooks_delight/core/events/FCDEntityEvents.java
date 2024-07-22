package com.uraneptus.frycooks_delight.core.events;

import com.uraneptus.frycooks_delight.FrycooksDelight;
import com.uraneptus.frycooks_delight.common.entity.OilPanicGoal;
import com.uraneptus.frycooks_delight.core.other.tags.FCDEntityTypeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FrycooksDelight.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FCDEntityEvents {

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof PathfinderMob mob) {
            if (mob.getType().is(FCDEntityTypeTags.PANIC_WHEN_OILED)) {
                mob.goalSelector.addGoal(0, new OilPanicGoal(mob));
            }
        }
    }

}