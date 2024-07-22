package com.uraneptus.frycooks_delight.common.entity;

import com.uraneptus.frycooks_delight.core.registry.FCDMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class OilPanicGoal extends Goal {
    protected final PathfinderMob mob;
    protected final double speedModifier;
    protected double posX;
    protected double posY;
    protected double posZ;

    public OilPanicGoal(PathfinderMob pMob) {
        this.mob = pMob;
        this.speedModifier = 1.25D;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (this.mob.hasEffect(FCDMobEffects.OILED.get())) {
            return this.findRandomPosition();
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        if (this.mob.hasEffect(FCDMobEffects.OILED.get())) {
            return this.findRandomPosition();
        }
        return false;
    }

    protected boolean findRandomPosition() {
        Vec3 vec3 = DefaultRandomPos.getPos(this.mob, 10, 5);
        if (vec3 == null) {
            return false;
        } else {
            this.posX = vec3.x;
            this.posY = vec3.y;
            this.posZ = vec3.z;
            return true;
        }
    }

    @Override
    public void start() {
        this.mob.getNavigation().moveTo(this.posX, this.posY, this.posZ, this.speedModifier);
    }
}
