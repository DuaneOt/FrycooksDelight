package com.uraneptus.frycooks_delight.common.advancement;

import com.google.common.base.Predicates;
import com.google.gson.JsonObject;
import com.uraneptus.frycooks_delight.FrycooksDelight;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class FryingIceTrigger extends SimpleCriterionTrigger<FryingIceTrigger.FryingIceCriterion> {
    public static final ResourceLocation ID = FrycooksDelight.modPrefix("frying_ice");
    public static final FryingIceTrigger INSTANCE = new FryingIceTrigger();

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    protected FryingIceCriterion createInstance(JsonObject json, ContextAwarePredicate predicate, DeserializationContext conditionsParser) {
        return new FryingIceCriterion(predicate);
    }

    public void trigger(ServerPlayer player) {
        this.trigger(player, Predicates.alwaysTrue());
    }

    public static class FryingIceCriterion extends AbstractCriterionTriggerInstance {

        public FryingIceCriterion(ContextAwarePredicate predicate) {
            super(ID, predicate);
        }

    }
}
